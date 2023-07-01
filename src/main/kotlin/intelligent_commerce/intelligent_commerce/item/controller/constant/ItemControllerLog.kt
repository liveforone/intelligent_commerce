package intelligent_commerce.intelligent_commerce.item.controller.constant

enum class ItemControllerLog(val log: String) {
    CREATE_ITEM_SUCCESS("상품 등록 성공"),
    UPDATE_TITLE_SUCCESS("상품명 변경 성공"),
    UPDATE_CONTENT_SUCCESS("상품 설명 변경 성공"),
    UPDATE_PRICE_SUCCESS("상품 가격 변경 성공"),
    ADD_REMAINING_SUCCESS("재고 추가 성공"),
    DELETE_ITEM_SUCCESS("상품 삭제 성공")
}