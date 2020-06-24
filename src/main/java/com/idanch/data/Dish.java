package com.idanch.data;

public class Dish {
    private String name;
    private int priceShekels;
    private String description;

    public Dish(String name, String description, int priceShekels) {
        setName(name);
        this.description = description;
        this.priceShekels = priceShekels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriceShekels() {
        return priceShekels;
    }

    public void setPriceShekels(int priceShekels) {
        this.priceShekels = priceShekels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        return name.equals(dish.name);
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode();
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priceShekels='" + priceShekels + '\'' +
                '}';
    }
}
