package com.impltech.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alex
 */
public class Calculator {

	public List<ResultCalculationEntity> calculate(PostHarvestManager data, BoxCapacity bc) {
		List<ResultCalculationEntity> calc = null;
		if (data != null && data.getCount_of_banch() != 0 && bc != null) {
			calc = new ArrayList<ResultCalculationEntity>();
			int cycleCount = 5 + (bc.getJb_count() == 0 ? 0 : 1);
			boolean isQb = bc.getQb_count() != 0;
			for (int i = 0; i < cycleCount; i++) {
				ResultCalculationEntity entity = null;
				if (i == 0) {
					entity = fillFB(data.getCount_of_banch(), bc);
				}
				if (i == 1) {
					entity = fillHB(data.getCount_of_banch(), bc);
				}
				if (i == 2 && isQb) {
					entity = fillQB(data.getCount_of_banch(), bc);
				}
				if (i == 3) {
					entity = fillBanch(data.getCount_of_banch(), bc);
				}
				if (i == 4) {
					entity = new ResultCalculationEntity();
					entity.setStamps(data.getCount_of_banch());
				}
				if (entity != null)
					calc.add(entity);
			}
		}
		return calc;

	}

	private ResultCalculationEntity fillFB(int stamps, BoxCapacity bc) {
		ResultCalculationEntity result = null;
		int fb = 0;
		fb = fillBox(stamps, bc.getFb_count());
		if (fb > 0) {
			result = new ResultCalculationEntity();
			stamps = stamps - fb * bc.getFb_count();
			result.setFb(fb);
			if (stamps > 0) {
				ResultCalculationEntity entity = fillHB(stamps, bc);
				if (entity != null) {
					result.setHb(entity.getHb());
					result.setBanch(entity.getBanch());
					result.setStamps(entity.getStamps());
					if (entity.getQb() != 0)
						result.setQb(entity.getQb());
				}
			}
		} else {
			return null;
		}

		return result;
	}

	private ResultCalculationEntity fillHB(int stamps, BoxCapacity bc) {
		ResultCalculationEntity result = null;
		int hb = 0;
		hb = fillBox(stamps, bc.getHb_count());
		if (hb > 0) {
			result = new ResultCalculationEntity();
			stamps = stamps - hb * bc.getHb_count();
			result.setHb(hb);
			if (stamps > 0) {
				if (bc.getQb_count() != 0) {
					ResultCalculationEntity entity = fillQB(stamps, bc);
					if (entity != null) {
						result.setQb(entity.getQb());
						result.setBanch(entity.getBanch());
						result.setStamps(entity.getStamps());
					}
				} else {
					ResultCalculationEntity entity = fillBanch(stamps, bc);
					if (entity != null) {
						result.setBanch(entity.getBanch());
						result.setStamps(entity.getStamps());
					}
				}
			}
		} else {
			return null;
		}

		return result;
	}

	private ResultCalculationEntity fillQB(int stamps, BoxCapacity bc) {
		ResultCalculationEntity result = null;
		int qb = 0;
		if (bc.getQb_count() != 0) {
			result = new ResultCalculationEntity();
			qb = fillBox(stamps, bc.getQb_count());
			if (qb > 0) {
				stamps = stamps - qb * bc.getQb_count();
				result.setQb(qb);
			}
			if (stamps > 0) {
				ResultCalculationEntity entity = fillBanch(stamps, bc);
				if (entity != null) {
					result.setBanch(entity.getBanch());
					result.setStamps(entity.getStamps());
				}
			}
		} else {
			return null;
		}
		return result;
	}

	private ResultCalculationEntity fillBanch(int stamps, BoxCapacity bc) {
		ResultCalculationEntity result = null;
		int banch = 0;
		banch = fillBox(stamps, bc.getBanch());
		if (banch > 0) {
			result = new ResultCalculationEntity();
			stamps = stamps - banch * bc.getBanch();
			result.setBanch(banch);
			if (stamps > 0) {
				result.setStamps(stamps);
			}
		} else {
			return null;
		}
		return result;
	}

	private int fillBox(int stamps, int capacity) {
		int result = 0;
		while (stamps > capacity) {
			result++;
			stamps -= capacity;
		}
		return result;
	}
}
