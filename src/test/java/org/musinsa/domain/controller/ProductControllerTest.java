package org.musinsa.domain.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.musinsa.domain.entity.Product;
import org.musinsa.domain.exception.SoldOutException;
import org.musinsa.domain.factory.Factory;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks private ProductController productController;

    @BeforeEach
    void setUp() {
        productController = Factory.createProductController();
    }

    /**
     * ITEM_782858(782858, "폴로 랄프로렌 남성 수영복반바지 컬렉션 (51color)", 39500, 50),
     * 10명의 고객이 10개씩 구매한다고 가정
     * 5명은 성공하고 5명은 실패 (SoldOutException 발생했을 경우 failCount 증가
     * 직접 SoldOutException 잡는 것이 아닌 failCount 증가로 판별하였습니다.
     */
    @Test
    void 주문_수량_초과_SoldOutException_발생() throws InterruptedException {
        Product product = Product.ITEM_782858;

        int numThreads = 10;
        CountDownLatch doneSignal = new CountDownLatch(numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        for (int i = 0; i < numThreads; i++){
            executorService.execute(() -> {
                try {
                    productController.executeOrder(product.getId(), 10);
                    successCount.getAndIncrement();
                } catch (SoldOutException e) {
                    failCount.getAndIncrement();
                } finally {
                    doneSignal.countDown();
                }
            });
        }

        doneSignal.await();
        executorService.shutdown();

        assertEquals(5, successCount.get());
        assertEquals(5, failCount.get());
    }
}