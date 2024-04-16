package org.musinsa.domain.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;
import org.musinsa.domain.exception.SoldOutException;
import org.musinsa.global.factory.SingletonFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;
    private final List<Order> orders = new ArrayList<>();

    @BeforeEach
    void setUp() {
        productController = SingletonFactory.createProductController();
    }

    /**
     * ITEM_782858(782858, "폴로 랄프로렌 남성 수영복반바지 컬렉션 (51color)", 39500, 50),
     * 100명의 고객이 1개씩 구매한다고 가정
     * 50명은 성공하고 50명은 실패 (SoldOutException 발생했을 경우 failCount 증가
     * 직접 SoldOutException 잡는 것이 아닌 catch를 통하여 failCount 증가로 판별하였습니다.
     */
    @Test
    @DisplayName("멀티 스레드환경에서 주문 수량을 초과하면 SoldOutException 발생한다.")
    void multiThreadedOrderExceedsStockThrowsSoldOut_success() throws InterruptedException {
        Product product = Product.ITEM_782858;

        int numThreads = 100;
        CountDownLatch doneSignal = new CountDownLatch(numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        for (int i = 0; i < numThreads; i++){
            executorService.execute(() -> {
                try {
                    productController.executeOrder(product.getId(), 1, orders);
                    successCount.getAndIncrement();
                } catch (SoldOutException e) { // SoldOutException 발생할 경우 failCount 증가
                    failCount.getAndIncrement();
                } finally {
                    doneSignal.countDown();
                }
            });
        }

        doneSignal.await();
        executorService.shutdown();

        assertThat(successCount.get()).isEqualTo(50);
        assertThat(failCount.get()).isEqualTo(50);
        assertThat(product.getStock()).isEqualTo(0);
    }

    @Test
    @DisplayName("수량이 70개의 상품에서 10개를 구매하면 60개가 된다.")
    void reduceStock_success() {
        Product product = Product.ITEM_760709;
        productController.processOrder(product.getId(), 10, orders);

        assertThat(product.getStock()).isEqualTo(60);
    }

    @Test
    @DisplayName("재고보다 많은 수량을 구매하면 SoldOutException 발생한다.")
    void OrderExceedsStockThrowsSoldOut_success() {
        Product product = Product.ITEM_768848;

        assertThatThrownBy(() -> productController.executeOrder(product.getId(), 50, orders))
                .isInstanceOf(SoldOutException.class)
                .hasMessageContaining("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
    }
}