import * as pulumi from "@pulumi/pulumi";
import { ProductInputs } from "../types/products.types";
import { ProductProvider } from "../providers/product.provider";


export class ProductResource extends pulumi.dynamic.Resource {
    
    public readonly name!: pulumi.Output<string>;

    constructor(name: string, props: ProductInputs, opts?: pulumi.CustomResourceOptions) {
        const config = new pulumi.Config("app");
        super(new ProductProvider(), name,
        {
            ...props,
            endpoint: config.require("endpoint")
        }, opts);
    }
}