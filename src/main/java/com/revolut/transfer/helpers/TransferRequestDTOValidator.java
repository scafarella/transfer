package com.revolut.transfer.helpers;

import com.revolut.transfer.dtos.TransferRequestDTO;
import com.revolut.transfer.exceptions.TransferRequestDTONotValidException;

import java.util.Objects;

import static com.revolut.transfer.Constants.ACCOUNT_NOT_VALID;
import static com.revolut.transfer.Constants.AMOUNT_NOT_VALID;

public class TransferRequestDTOValidator {

    public static void validate(final TransferRequestDTO transferRequestDTO) throws TransferRequestDTONotValidException {

        validateAccount(transferRequestDTO.getFromAccount());
        validateAccount(transferRequestDTO.getToAccount());
        validateAmount(transferRequestDTO.getAmount());
        validateIfSameAccount(transferRequestDTO.getFromAccount(), transferRequestDTO.getToAccount());

    }

    private static void validateIfSameAccount(Long fromAccount, Long toAccount) throws TransferRequestDTONotValidException {
        if(fromAccount.equals(toAccount)){
            throw new TransferRequestDTONotValidException(ACCOUNT_NOT_VALID);
        }
    }

    private static void validateAmount(Double amount) throws TransferRequestDTONotValidException {

        if(Objects.isNull(amount) || amount <= 0){
            throw new TransferRequestDTONotValidException(AMOUNT_NOT_VALID);
        }
    }

    private static void validateAccount(final Long account) throws TransferRequestDTONotValidException {

        if(Objects.isNull(account) || account < 0){
            throw new TransferRequestDTONotValidException(ACCOUNT_NOT_VALID);
        }
    }

}
