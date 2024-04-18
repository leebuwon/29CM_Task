package org.musinsa.domain.product.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.musinsa.domain.order.exception.SoldOutException;

@Getter
@ToString
@AllArgsConstructor
public enum Product {
    ITEM_768848(768848, "[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종", 21000, 45),
    ITEM_748943(748943, "디오디너리 데일리 세트 (Daily set)", 19000, 89),
    ITEM_779989(779989, " 버드와이저 HOME DJing 굿즈 세트", 35000, 43),
    ITEM_779943(779943, "Fabrik Pottery Flat Cup & Saucer - Mint", 24900, 89),
    ITEM_768110(768110, "네페라 손 세정제 대용량 500ml 이더블유지", 7000, 79),
    ITEM_517643(517643, "에어팟프로 AirPods PRO 블루투스 이어폰(MWP22KH/A)", 260800, 26),
    ITEM_706803(706803, "ZEROVITY™ Flip Flop Cream 2.0 (Z-FF-CRAJ-)", 38000, 81),
    ITEM_759928(759928, "마스크 스트랩 분실방지 오염방지 목걸이", 2800, 85),
    ITEM_213341(213341, "20SS 오픈 카라/투 버튼 피케 티셔츠 (6color)", 33250, 99),
    ITEM_377169(377169, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 24900, 60),
    ITEM_744775(744775, "SHUT UP [TK00112]", 28000, 35),
    ITEM_779049(779049, "[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)", 10000, 64),
    ITEM_611019(611019, "플루크 new 피그먼트 오버핏 반팔티셔츠 FST701 / 7color M", 19800, 7),
    ITEM_628066(628066, "무설탕 프로틴 초콜릿 틴볼스", 12900, 8),
    ITEM_502480(502480, "[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)", 24900, 41),
    ITEM_782858(782858, "폴로 랄프로렌 남성 수영복반바지 컬렉션 (51color)", 39500, 50),
    ITEM_760709(760709, "파버카스텔 연필1자루", 200, 70),
    ITEM_778422(778422, "캠핑덕 우드롤테이블", 45000, 7),
    ITEM_648418(648418, "BS 02-2A DAYPACK 26 (BLACK)", 238000, 5);

    private final int id;
    private final String name;
    private final int price;
    private int stock;

    /**
     * 재고 감소
     * 재고가 주문 수량보다 적다면 exception
     */
    public void reduceStock(int quantity) {
        if (this.stock >= quantity) {
            this.stock -= quantity;
            return;
        }

        throw new SoldOutException("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
    }
}

