package com.tmc.clutterspace.core.collision;

public class CollisionDict {
	public final static short CATEGORY_PLAYER = 0x0001; // 0000000000000001 in binary
	public final static short CATEGORY_PROJECTILE = 0x0002; // 0000000000000010 in binary
	public final static short CATEGORY_GROUND = 0x0004; // 0000000000000100 in binary
	
	public final static short MASK_PLAYER = CATEGORY_PROJECTILE | CATEGORY_GROUND;
	public final static short MASK_PROJECTILE = CATEGORY_GROUND | CATEGORY_PLAYER;
	public final static short MASK_GROUND = -1;
}
