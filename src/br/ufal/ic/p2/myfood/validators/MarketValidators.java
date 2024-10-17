package br.ufal.ic.p2.myfood.validators;

import br.ufal.ic.p2.myfood.exceptions.InvalidAttributeException;

public class MarketValidators {
    public static void validateFields(String openingHour, String closingHour, String marketType) {
        if (!CommonValidators.isValidTimeFormat(openingHour) || !CommonValidators.isValidTimeFormat(closingHour)) {
            throw new InvalidAttributeException("Formato de hora invalido");
        }

        if (!CommonValidators.isValidTimeRange(openingHour, closingHour)) {
            throw new InvalidAttributeException("Horario invalido");
        }

        if (CommonValidators.isNullOrEmpty(marketType)) {
            throw new InvalidAttributeException("Tipo de mercado invalido");
        }
    }
}
