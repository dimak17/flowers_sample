package com.impltech.validator.util;

import com.impltech.domain.Block;

/**
 * @author alex
 */
public class BlockValidator {

	public static boolean checkName(Block block) {
		if (block != null && !block.getName().isEmpty()) {
			return block.getName().length() < 11;
		}
		return false;
	}

	public static boolean checkBeds(Block block) {
		if (block != null && block.getBeds() != 0) {
			return block.getBeds() < 99999;
		}
		return false;
	}

}
