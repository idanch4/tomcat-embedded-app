package com.idanch.data.representations;

//TODO:: make this class immutable
public final class Dish {
    private long id;
    private String name;
    private String description;
    private String category;
    private double priceShekels;

    public Dish() {}

    public Dish(long id, String name, String description, String category, int priceShekels) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.priceShekels = priceShekels;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPriceShekels() {
        return priceShekels;
    }

    public void setPriceShekels(double priceShekels) {
        this.priceShekels = priceShekels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        return id == dish.id;
    }

    @Override
    public int hashCode() {
        return 31 * (int) id;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f NIS): %s", name, priceShekels,
                description == null ? "" : description);
    }
}
