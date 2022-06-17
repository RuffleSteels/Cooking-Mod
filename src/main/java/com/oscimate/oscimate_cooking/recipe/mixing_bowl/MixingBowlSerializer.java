package com.oscimate.oscimate_cooking.recipe.mixing_bowl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.oscimate.oscimate_cooking.Main;
import com.oscimate.oscimate_cooking.recipe.kitchen_bench.KitchenBenchShapeless;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MixingBowlSerializer implements RecipeSerializer<MixingBowlRecipe> {

    public static final MixingBowlSerializer INSTANCE = new MixingBowlSerializer();
    public static final Identifier ID = new Identifier("oscimate_cooking:mixing_bowl_recipe");

    private MixingBowlSerializer(){}

    public MixingBowlRecipe read(Identifier id, JsonObject json) {
        MixingBowlJsonFormat recipeJson = new Gson().fromJson(json, MixingBowlJsonFormat.class);

        // Validate all fields are there
        if (recipeJson.inputA == null || recipeJson.inputB == null || recipeJson.inputC == null || recipeJson.outputItem == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }

        // We'll allow to not specify the output, and default it to 1.
        if (recipeJson.outputAmount == 0) recipeJson.outputAmount = 1;

        Ingredient inputA = Ingredient.fromJson(recipeJson.inputA);
        Ingredient inputB = Ingredient.fromJson(recipeJson.inputB);
        Ingredient inputC = Ingredient.fromJson(recipeJson.inputC);
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem)).orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.outputItem));

        ItemStack output = new ItemStack(outputItem, recipeJson.outputAmount);

        return new MixingBowlRecipe(id, output, inputA, inputB, inputC);
    }

    @Override
    public void write(PacketByteBuf buf, MixingBowlRecipe recipe) {
        recipe.getInputA().write(buf);
        recipe.getInputB().write(buf);
        recipe.getInputC().write(buf);
        buf.writeItemStack(recipe.getOutput());
    }

    @Override
    public MixingBowlRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient inputA = Ingredient.fromPacket(buf);
        Ingredient inputB = Ingredient.fromPacket(buf);
        Ingredient inputC = Ingredient.fromPacket(buf);
        ItemStack output = buf.readItemStack();
        return new MixingBowlRecipe(id, output, inputA, inputB, inputC);
    }

}
