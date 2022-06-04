package com.oscimate.oscimate_cooking.recipe.kitchen_bench;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.oscimate.oscimate_cooking.Main;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class KitchenBenchSerializer implements RecipeSerializer<KitchenBenchRecipe> {

    private KitchenBenchSerializer() {
    }

    public static final KitchenBenchSerializer INSTANCE = new KitchenBenchSerializer();

    public static final Identifier ID = new Identifier(Main.MODID, "kitchen_bench");

    @Override // Turns json into Recipe
    public KitchenBenchRecipe read(Identifier recipeId, JsonObject json) {
        KitchenBenchJsonFormat recipeJson = new Gson().fromJson(json, KitchenBenchJsonFormat.class);
        if (recipeJson.input1 == null ||
                recipeJson.input2 == null ||
                recipeJson.input3 == null ||
                recipeJson.input4 == null ||
                recipeJson.input5 == null ||
                recipeJson.input6 == null ||
                recipeJson.input7 == null ||
                recipeJson.input8 == null ||
                recipeJson.input9 == null ||
                recipeJson.outputItem == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
        if (recipeJson.outputAmount == 0) recipeJson.outputAmount = 1;

        Ingredient input1 = Ingredient.fromJson(recipeJson.input1);
        Ingredient input2 = Ingredient.fromJson(recipeJson.input2);
        Ingredient input3 = Ingredient.fromJson(recipeJson.input3);
        Ingredient input4 = Ingredient.fromJson(recipeJson.input4);
        Ingredient input5 = Ingredient.fromJson(recipeJson.input5);
        Ingredient input6 = Ingredient.fromJson(recipeJson.input6);
        Ingredient input7 = Ingredient.fromJson(recipeJson.input7);
        Ingredient input8 = Ingredient.fromJson(recipeJson.input8);
        Ingredient input9 = Ingredient.fromJson(recipeJson.input9);
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.outputItem));
        ItemStack output = new ItemStack(outputItem, recipeJson.outputAmount);

        return new KitchenBenchRecipe(input1, input2, input3, input4, input5, input6, input7, input8, input9, output, recipeId);
    }
    @Override // Turns Recipe into PacketByteBuf
    public void write(PacketByteBuf packetData, KitchenBenchRecipe recipe) {
        recipe.getInput1().write(packetData);
        recipe.getInput2().write(packetData);
        recipe.getInput3().write(packetData);
        recipe.getInput4().write(packetData);
        recipe.getInput5().write(packetData);
        recipe.getInput6().write(packetData);
        recipe.getInput7().write(packetData);
        recipe.getInput8().write(packetData);
        recipe.getInput9().write(packetData);
        packetData.writeItemStack(recipe.getOutput());
    }

    @Override // Turns PacketByteBuf into Recipe
    public KitchenBenchRecipe read(Identifier recipeId, PacketByteBuf packetData) {
        Ingredient input1 = Ingredient.fromPacket(packetData);
        Ingredient input2 = Ingredient.fromPacket(packetData);
        Ingredient input3 = Ingredient.fromPacket(packetData);
        Ingredient input4 = Ingredient.fromPacket(packetData);
        Ingredient input5 = Ingredient.fromPacket(packetData);
        Ingredient input6 = Ingredient.fromPacket(packetData);
        Ingredient input7 = Ingredient.fromPacket(packetData);
        Ingredient input8 = Ingredient.fromPacket(packetData);
        Ingredient input9 = Ingredient.fromPacket(packetData);
        ItemStack output = packetData.readItemStack();
        return new KitchenBenchRecipe(input1, input2, input3, input4, input5, input6, input7, input8, input9, output, recipeId);
    }

}
