package com.inventorycontrolapi.unit.domains;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.inventorycontrolapi.domains.ItemCategoryDomain;
import com.inventorycontrolapi.domains.exceptions.InvalidItemCategoryDomainException;

@ExtendWith(SpringExtension.class)
public class ItemCategoryDomainTests {
	@Test
	public void retornaItemCategoryDomain() throws Exception {
		ItemCategoryDomain itemCategoryDomain = ItemCategoryDomain.validate("Item A");	

		Assertions.assertThat(itemCategoryDomain.getName()).isEqualTo("Item A");
	}
	
	@Test
	public void retornaException_NameComValorVazio() throws Exception {		
		Assertions.assertThatExceptionOfType(InvalidItemCategoryDomainException.class)
			.isThrownBy(() -> ItemCategoryDomain.validate(""))
			.withMessage("name: must not be blank");
	}
}
