package com.reasure.practice.block.client.model;

import net.minecraft.client.resources.model.ModelState;
import org.jetbrains.annotations.Nullable;

/**
 * A key used to identify a set of baked quads for the baked model
 */
public record ModelKey(boolean generating, boolean collecting, boolean actuallyGenerating,
                       @Nullable ModelState modelState) {}
