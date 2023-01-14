import * as pulumi from "@pulumi/pulumi";
import { Product } from "./mock/product";

const product = new Product("product", {
    name: "Product 1",
    brand: "Brand 1",
    price: "100",
    category: "Shirt"
});

export const productId = product.id;
export const productName = product.name;