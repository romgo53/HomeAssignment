import * as pulumi from "@pulumi/pulumi";
import { ProductInputs } from "../types/products.types";
import { ProductProvider } from "../providers/product.provider";
import { apiUrl } from "../constants/app.constants";

export class ProductResource extends pulumi.dynamic.Resource {
  public readonly name!: pulumi.Output<string>;

  constructor(
    name: string,
    props: ProductInputs,
    opts?: pulumi.CustomResourceOptions
  ) {

    super(
      new ProductProvider(),
      name,
      {
        ...props,
        endpoint: apiUrl,
      },
      opts
    );
  }
}
