package com.app.app.DTO;

public class ItemDTO {
    private Long itemId;
    private String itemName;
    private Double price;
    private Integer stockAvailability;
    private String categoryName;

    public ItemDTO(Long itemId, String itemName, Double price, Integer stockAvailability,String categoryName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.stockAvailability = stockAvailability;
        this.categoryName = categoryName;
    }

    public Long getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public Double getPrice() { return price; }
    public Integer getStockAvailability() { return stockAvailability; }
    public String getCategoryName() { return categoryName; }
}

