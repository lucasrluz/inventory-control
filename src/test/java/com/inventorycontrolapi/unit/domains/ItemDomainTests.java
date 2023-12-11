package com.inventorycontrolapi.unit.domains;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.inventorycontrolapi.domains.ItemDomain;
import com.inventorycontrolapi.domains.exceptions.InvalidItemDomainException;

@ExtendWith(SpringExtension.class)
public class ItemDomainTests {
	@Test
	public void retornaItemDomain() throws Exception {
		ItemDomain itemDomain = ItemDomain.validate("Item A");

		Assertions.assertThat(itemDomain.getName()).isEqualTo("Item A");
	}
	
	@Test
	public void retornaException_NameComValorVazio() throws Exception {
		Assertions.assertThatExceptionOfType(InvalidItemDomainException.class)
			.isThrownBy(() -> ItemDomain.validate(""))
			.withMessage("name: must not be blank");
	}
}
