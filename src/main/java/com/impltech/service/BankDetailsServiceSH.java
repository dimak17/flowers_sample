package com.impltech.service;

import com.impltech.domain.BankDetails;
import com.impltech.reports.bank_details.BankDetailsDesign;
import com.impltech.repository.BankDetailsRepositorySH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author dima
 * Service Implementation for managing BankDetails.
 */
@Service
@Transactional
public class BankDetailsServiceSH {

    private final Logger log = LoggerFactory.getLogger(BankDetailsServiceSH.class);

    private final BankDetailsRepositorySH BankDetailsRepositorySH;

    public BankDetailsServiceSH(BankDetailsRepositorySH BankDetailsRepositorySH) {
        this.BankDetailsRepositorySH = BankDetailsRepositorySH;
    }

    /**
     * Save a bankDetails.
     *
     * @param bankDetails the entity to save
     * @return the persisted entity
     */
    public BankDetails save(BankDetails bankDetails) {
        log.debug("Request to save BankDetails : {}", bankDetails);
        return BankDetailsRepositorySH.save(bankDetails);
    }

    /**
     *  Get all the bankDetails.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BankDetails> findAll() {
        log.debug("Request to get all BankDetails");
        return BankDetailsRepositorySH.findAll();
    }

    /**
     *  Get one bankDetails by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BankDetails findOne(Long id) {
        log.debug("Request to get BankDetails : {}", id);
        return BankDetailsRepositorySH.findOne(id);
    }

    /**
     *  Delete the  bankDetails by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BankDetails : {}", id);
        BankDetailsRepositorySH.delete(id);
    }

    public BankDetails findBankDetailsByCompanyId(){
//        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        log.debug("Request to get BankDetailsByIdCompany where id is " + 1);
            return BankDetailsRepositorySH.findBankDetailsByCompanyId(1L);
    }

    public static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        String date = dtf.format(localDate);
        return date;
    }

    public final static void cleanDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File aFile : files) {
                    aFile.delete();
                }
            }
        }
    }

    public boolean setReportDataByCheckbox(Long id, String filePath, String companyName, String date) throws FileNotFoundException {
        BankDetails bankDetails = findBankDetailsByCompanyId();

        if (id.equals(1L)) {
            BankDetailsDesign.bankDetailsReportBuilder(companyName, date, filePath, bankDetails.getGeneral() + "\n\n" + bankDetails.getAlternative());
        } else if (id.equals(2L)) {
            BankDetailsDesign.bankDetailsReportBuilder(companyName, date, filePath, bankDetails.getGeneral());
        } else if (id.equals(3L)) {
            BankDetailsDesign.bankDetailsReportBuilder(companyName, date, filePath, bankDetails.getAlternative());
        } else {
            return false;
        }
        return true;
    }
}
