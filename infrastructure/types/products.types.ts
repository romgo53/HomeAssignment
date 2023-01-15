import * as pulumi from "@pulumi/pulumi";

// Inputs
export interface ProductInputs {
  name: pulumi.Input<string>;
  brand: pulumi.Input<string>;
  price: pulumi.Input<string>;
  category: pulumi.Input<string>;
}

export interface ProductProviderInputs {
  name: string;
  brand: string;
  price: string;
  category: string;
  // Api endpoint set in the config
  endpoint: string;
}

// Responses
export interface ProductRespose {
  id: string;
  message: string;
}

// Outputs
export interface ProductProviderOutputs {
  id: string;
  outs: ProductProviderInputs;
}
