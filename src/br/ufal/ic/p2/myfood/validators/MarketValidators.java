package br.ufal.ic.p2.myfood.validators;

import br.ufal.ic.p2.myfood.exceptions.InvalidAttributeException;

/**
 * Validator class for market-related operations.
 */
public class MarketValidators {

    /**
     * Validates the fields for market attributes such as opening hour, closing hour, and market type.
     *
     * @param openingHour the opening hour of the market in the format HH:mm.
     * @param closingHour the closing hour of the market in the format HH:mm.
     * @param marketType  the type of the market.
     * @throws InvalidAttributeException if any of the fields are invalid.
     */
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
