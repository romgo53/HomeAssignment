import * as pulumi from "@pulumi/pulumi";
import { ProductInputs } from "../types/products";
import { ProductProvider } from "../providers/productProvider";


export class Product extends pulumi.dynamic.Resource {
    
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