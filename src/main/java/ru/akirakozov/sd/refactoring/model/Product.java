package ru.akirakozov.sd.refactoring.model;

final public class Product {
    private final String name;
    private final int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Product) {
            Product otherProduct = (Product) other;
            return otherProduct.name.equals(this.name) &&
                    otherProduct.price == this.price;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + price;
    }

    @Override
    public String toString() {
        return "Product(\n\t" + name + "\n\t" + price + "\n)";
    }
}
