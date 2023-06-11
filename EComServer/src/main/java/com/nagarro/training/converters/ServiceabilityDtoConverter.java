package com.nagarro.training.converters;

import com.nagarro.training.dto.ServiceabilityDto;
import com.nagarro.training.models.Serviceability;

public class ServiceabilityDtoConverter {
	public ServiceabilityDto convertEntityToDto(Serviceability serviceability) {

		ServiceabilityDto serviceabilityDto = new ServiceabilityDto();

		serviceabilityDto.setPincode(serviceability.getPincode());

		serviceabilityDto.setExpectedDelivery(DateConverter.covertDaysIntoDate(serviceability.getExpectedDelivery()));

		return serviceabilityDto;
	}

	public Serviceability convertDtoToEntity(ServiceabilityDto serviceabilityDto) {
		Serviceability serviceability = new Serviceability();

		serviceability.setPincode(serviceabilityDto.getPincode());
		serviceability.setExpectedDelivery(Integer.parseInt(serviceabilityDto.getExpectedDelivery()));

		return serviceability;
	}
}
