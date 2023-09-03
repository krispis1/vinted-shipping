package lt.vinted.shipping.controller;

import lt.vinted.shipping.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String getTransactionsList(@RequestPart("file") MultipartFile inputFile) throws IOException {
        String input = new String(inputFile.getBytes());
        return transactionService.getTransactions(input);
    }
}
