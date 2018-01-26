package com.impltech.calculator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {
	List<ResultCalculationEntity> expecteds = new ArrayList<>();
	BoxCapacity bc = new BoxCapacity();
	PostHarvestManager data = new PostHarvestManager();

	@Before
	public void setup(){
		ResultCalculationEntity entity0= new ResultCalculationEntity();
		entity0.setFb(3);
		entity0.setHb(1);
		entity0.setQb(0);
		entity0.setBanch(2);
		entity0.setStamps(23);

		ResultCalculationEntity entity1 = new ResultCalculationEntity();
		entity1.setHb(7);
		entity1.setQb(0);
		entity1.setBanch(2);
		entity1.setStamps(23);

		ResultCalculationEntity entity2 = new ResultCalculationEntity();
		entity2.setQb(14);
		entity2.setBanch(2);
		entity2.setStamps(23);

		ResultCalculationEntity entity3 = new ResultCalculationEntity();
		entity3.setBanch(100);
		entity3.setStamps(23);

		ResultCalculationEntity entity4 = new ResultCalculationEntity();
		entity4.setStamps(2523);

		expecteds.add(entity0);
		expecteds.add(entity1);
		expecteds.add(entity2);
		expecteds.add(entity3);
		expecteds.add(entity4);

		bc.setFb_count(700);
		bc.setHb_count(350);
		bc.setQb_count(175);
		bc.setBanch(25);

		data.setCount_of_banch(2523);
	}

	@Test
	public void testCalculatorWithQb(){

		Calculator calc = new Calculator();
		assertEquals(expecteds, calc.calculate(data, bc));
	}
}

