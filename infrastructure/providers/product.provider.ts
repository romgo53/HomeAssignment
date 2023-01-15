import * as pulumi from "@pulumi/pulumi";
import axios from "axios";
import { ProductProviderOutputs, ProductProviderInputs, ProductRespose } from "../types/products.types";

export class ProductProvider implements pulumi.dynamic.ResourceProvider {
  async create(inputs: ProductProviderInputs): Promise<ProductProviderOutputs> {
    const result = await axios.post<ProductRespose>(`${inputs.endpoint}/product`, inputs, {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    });
    if (result.status !== 200) {
      throw new Error(`Error ${result.status}`);
    }
    const id = result.data.id;
    return {
      id,
      outs: {
        ...inputs,
        endpoint: inputs.endpoint
      },
    };
  }
  async delete(id: string, props: any) {
    const result = await axios.delete(`${props.endpoint}/product/${id}`);

    if (result.status !== 200) {
      throw new Error(`Error ${result.status}`);
    }
  }
}
