import * as pulumi from "@pulumi/pulumi";
import { ProductResource } from "./resources/product.resource";

const product = new ProductResource("product", {
    name: "Product 1",
    brand: "Brand 1",
    price: "100",
    category: "Shirt"
});

export const productId = product.id;
export const productName = product.name;