package com.impltech.config;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.impltech.domain.*;
import com.impltech.domain.enumeration.MarketVarietyType;
import com.impltech.repository.*;
import com.impltech.security.AuthoritiesConstants;
import com.impltech.service.UserService;
import com.impltech.service.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by platon
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOG = Logger.getLogger(ApplicationStartup.class.getName());

    private final CompanyRepository companyRepository;
    private final PositionRepository positionRepository;
    private final UserService userService;
    private final CompanyUserRepository companyUserRepository;
    private final AuthorityRepository authorityRepository;
    private final BoxGroupRepository boxGroupRepository;
    private final BoxTypeGroupRepository boxTypeGroupRepository;
    private final BoxTypeRepository boxTypeRepository;
    private final ShippingPolicyRepository shippingPolicyRepository;
    private final ClaimsPolicyRepository claimsPolicyRepository;
    private final PaymentPolicyRepository paymentPolicyRepository;
    private final BankDetailsRepositorySH bankDetailsRepositorySH;
    private final MarketRepositorySH marketRepository;
    private final VarietyRepository varietyRepository;
    private final BlockRepository blockRepository;
    private final AirLinesRepositorySH airLinesRepositorySH;
    private final TypeOfFlowerRepository typeOfFlowerRepository;
    private final MixTypeRepository mixTypeRepository;
    private final MarketVarietyRepositorySH marketVarietyRepositorySH;
    private final MarketVarietyPropertyPriceListRepository marketVarietyPropertyPriceListRepository;
    private final SeasonRepository seasonRepository;
    private final MarketSeasonRepository marketSeasonRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public ApplicationStartup(
        CompanyRepository companyRepository,
        PositionRepository positionRepository,
        UserService userService,
        CompanyUserRepository companyUserRepository,
        BoxGroupRepository boxGroupRepository,
        BoxTypeGroupRepository boxTypeGroupRepository,
        BoxTypeRepository boxTypeRepository,
        ShippingPolicyRepository shippingPolicyRepository,
        ClaimsPolicyRepository claimsPolicyRepository,
        PaymentPolicyRepository paymentPolicyRepository,
        BankDetailsRepositorySH bankDetailsRepositorySH,
        VarietyRepository varietyRepository,
        BlockRepository blockRepository,
        AuthorityRepository authorityRepository,
        MarketRepositorySH marketRepository,
        AirLinesRepositorySH airLinesRepositorySH,
        TypeOfFlowerRepository typeOfFlowerRepository,
        MixTypeRepository mixTypeRepository,
        MarketVarietyRepositorySH marketVarietyRepositorySH,
        MarketVarietyPropertyPriceListRepository marketVarietyPropertyPriceListRepository,
        SeasonRepository seasonRepository,
        MarketSeasonRepository marketSeasonRepository,
        ClientRepository clientRepository
    ) {
            this.companyRepository = companyRepository;
            this.positionRepository = positionRepository;
            this.userService = userService;
            this.companyUserRepository = companyUserRepository;
            this.authorityRepository = authorityRepository;
            this.boxGroupRepository = boxGroupRepository;
            this.boxTypeGroupRepository = boxTypeGroupRepository;
            this.boxTypeRepository = boxTypeRepository;
            this.shippingPolicyRepository = shippingPolicyRepository;
            this.claimsPolicyRepository = claimsPolicyRepository;
            this.paymentPolicyRepository = paymentPolicyRepository;
            this.bankDetailsRepositorySH = bankDetailsRepositorySH;
            this.airLinesRepositorySH = airLinesRepositorySH;
            this.varietyRepository = varietyRepository;
            this.blockRepository = blockRepository;
            this.typeOfFlowerRepository = typeOfFlowerRepository;
            this.mixTypeRepository = mixTypeRepository;
            this.marketRepository = marketRepository;
            this.marketVarietyRepositorySH = marketVarietyRepositorySH;
            this.marketVarietyPropertyPriceListRepository = marketVarietyPropertyPriceListRepository;
            this.seasonRepository = seasonRepository;
            this.marketSeasonRepository = marketSeasonRepository;
            this.clientRepository = clientRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        LOG.info("Application startup event triggered-------------");
        try {
            Thread.sleep(7000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fillDb();
    }

    @Transactional
    public void fillDb() {
        fillCompany();
        fillBoxType();
        fillShippingPolicy();
        fillClaimsPolicy();
        fillPaymentPolicy();
        fillBoxGroupings();
        fillBankDetails();
        fillTypeOfFlower();
        fillVariety();
        fillBlock();
        fillMixType();
        fillMarkets();
        fillMarketVarieties();
        fillCompanyUser();
        fillAirLines();
        fillMarketSeasons();
        fillSeasons();
        fillClients();
    }

    private void fillMarkets() {
        if(this.marketRepository.count() == 0) {
            this.marketRepository.save(Arrays.asList(
                new Market().name("Russian").company(createCompany()),
                new Market().name("American").company(createCompany()),
                new Market().name("Moldovan").company(createCompany()),
                new Market().name("Japanese").company(createCompany())
            ));
        }
    }

    private void fillBoxGroupings() {
        List<BoxGroup> groups = boxGroupRepository.findByCompanyId(createCompany().getId());
        if(groups != null && groups.size() < 1) {
            BoxGroup boxGroup1 = new BoxGroup();
            boxGroup1.setCompany(createCompany());
            boxGroup1 = boxGroupRepository.save(boxGroup1);
            fillBoxTypeGroup1(boxGroup1);

            BoxGroup boxGroup2 = new BoxGroup();
            boxGroup2.setCompany(createCompany());
            boxGroup2 = boxGroupRepository.save(boxGroup2);
            fillBoxTypeGroup2(boxGroup2);

            BoxGroup boxGroup3 = new BoxGroup();
            boxGroup3.setCompany(createCompany());
            boxGroup3 = boxGroupRepository.save(boxGroup3);
            fillBoxTypeGroup3(boxGroup3);

            BoxGroup boxGroup4 = new BoxGroup();
            boxGroup4.setCompany(createCompany());
            boxGroup4 = boxGroupRepository.save(boxGroup4);
            fillBoxTypeGroup4(boxGroup4);

            BoxGroup boxGroup5 = new BoxGroup();
            boxGroup5.setCompany(createCompany());
            boxGroup5 = boxGroupRepository.save(boxGroup5);
            fillBoxTypeGroup5(boxGroup5);

            BoxGroup boxGroup6 = new BoxGroup();
            boxGroup6.setCompany(createCompany());
            boxGroup6 = boxGroupRepository.save(boxGroup6);
            fillBoxTypeGroup6(boxGroup6);

            BoxGroup boxGroup7 = new BoxGroup();
            boxGroup7.setCompany(createCompany());
            boxGroup7 = boxGroupRepository.save(boxGroup7);
            fillBoxTypeGroup7(boxGroup7);
        }
    }

    private Set<BoxTypeGroup> fillBoxTypeGroup7(BoxGroup boxGroup) {
        List<BoxType> boxTypes = boxTypeRepository.findByCompanyId(createCompany().getId());

        BoxType qbBox = boxTypes.stream().filter(b -> b.getShortName().equals("QB")).findFirst().get();

        BoxTypeGroup boxTypeGroup3 = new BoxTypeGroup();
        boxTypeGroup3.setQuantity(1);
        boxTypeGroup3.setOrder(0);
        boxTypeGroup3.setBoxType(qbBox);
        boxTypeGroup3.setBoxGroup(boxGroup);

        return new HashSet<>(boxTypeGroupRepository.save(Arrays.asList(
            boxTypeGroup3
        )));
    }

    private Set<BoxTypeGroup> fillBoxTypeGroup6(BoxGroup boxGroup) {
        List<BoxType> boxTypes = boxTypeRepository.findByCompanyId(createCompany().getId());

        BoxType hbBox = boxTypes.stream().filter(b -> b.getShortName().equals("HB")).findFirst().get();

        BoxTypeGroup boxTypeGroup2 = new BoxTypeGroup();
        boxTypeGroup2.setQuantity(1);
        boxTypeGroup2.setOrder(0);
        boxTypeGroup2.setBoxType(hbBox);
        boxTypeGroup2.setBoxGroup(boxGroup);

        return new HashSet<>(boxTypeGroupRepository.save(Arrays.asList(
            boxTypeGroup2
        )));
    }

    private Set<BoxTypeGroup> fillBoxTypeGroup5(BoxGroup boxGroup) {
        List<BoxType> boxTypes = boxTypeRepository.findByCompanyId(createCompany().getId());

        BoxType fbBox = boxTypes.stream().filter(b -> b.getShortName().equals("FB")).findFirst().get();

        BoxTypeGroup boxTypeGroup1 = new BoxTypeGroup();
        boxTypeGroup1.setQuantity(1);
        boxTypeGroup1.setOrder(0);
        boxTypeGroup1.setBoxType(fbBox);
        boxTypeGroup1.setBoxGroup(boxGroup);

        return new HashSet<>(boxTypeGroupRepository.save(Arrays.asList(
            boxTypeGroup1
        )));
    }

    private  Set<BoxTypeGroup> fillBoxTypeGroup4(BoxGroup boxGroup) {
        List<BoxType> boxTypes = boxTypeRepository.findByCompanyId(createCompany().getId());

        BoxType fbBox = boxTypes.stream().filter(b -> b.getShortName().equals("FB")).findFirst().get();
        BoxType qbBox = boxTypes.stream().filter(b -> b.getShortName().equals("QB")).findFirst().get();

        BoxTypeGroup boxTypeGroup2 = new BoxTypeGroup();
        boxTypeGroup2.setQuantity(1);
        boxTypeGroup2.setOrder(0);
        boxTypeGroup2.setBoxType(fbBox);
        boxTypeGroup2.setBoxGroup(boxGroup);

        BoxTypeGroup boxTypeGroup3 = new BoxTypeGroup();
        boxTypeGroup3.setQuantity(4);
        boxTypeGroup3.setOrder(1);
        boxTypeGroup3.setBoxType(qbBox);
        boxTypeGroup3.setBoxGroup(boxGroup);

        return new HashSet<>(boxTypeGroupRepository.save(Arrays.asList(
            boxTypeGroup2,
            boxTypeGroup3
        )));
    }


    private  Set<BoxTypeGroup> fillBoxTypeGroup3(BoxGroup boxGroup) {
        List<BoxType> boxTypes = boxTypeRepository.findByCompanyId(createCompany().getId());

        BoxType hbBox = boxTypes.stream().filter(b -> b.getShortName().equals("HB")).findFirst().get();
        BoxType qbBox = boxTypes.stream().filter(b -> b.getShortName().equals("QB")).findFirst().get();

        BoxTypeGroup boxTypeGroup2 = new BoxTypeGroup();
        boxTypeGroup2.setQuantity(1);
        boxTypeGroup2.setOrder(0);
        boxTypeGroup2.setBoxType(hbBox);
        boxTypeGroup2.setBoxGroup(boxGroup);

        BoxTypeGroup boxTypeGroup3 = new BoxTypeGroup();
        boxTypeGroup3.setQuantity(2);
        boxTypeGroup3.setOrder(1);
        boxTypeGroup3.setBoxType(qbBox);
        boxTypeGroup3.setBoxGroup(boxGroup);

        return new HashSet<>(boxTypeGroupRepository.save(Arrays.asList(
            boxTypeGroup2,
            boxTypeGroup3
        )));
    }

    private Set<BoxTypeGroup> fillBoxTypeGroup2(BoxGroup boxGroup) {
        List<BoxType> boxTypes = boxTypeRepository.findByCompanyId(createCompany().getId());

        BoxType fbBox = boxTypes.stream().filter(b -> b.getShortName().equals("FB")).findFirst().get();
        BoxType hbBox = boxTypes.stream().filter(b -> b.getShortName().equals("HB")).findFirst().get();

        BoxTypeGroup boxTypeGroup1 = new BoxTypeGroup();
        boxTypeGroup1.setQuantity(1);
        boxTypeGroup1.setOrder(0);
        boxTypeGroup1.setBoxType(fbBox);
        boxTypeGroup1.setBoxGroup(boxGroup);

        BoxTypeGroup boxTypeGroup2 = new BoxTypeGroup();
        boxTypeGroup2.setQuantity(2);
        boxTypeGroup2.setOrder(1);
        boxTypeGroup2.setBoxType(hbBox);
        boxTypeGroup2.setBoxGroup(boxGroup);

        return new HashSet<>(boxTypeGroupRepository.save(Arrays.asList(
            boxTypeGroup1,
            boxTypeGroup2
        )));
    }

    private Set<BoxTypeGroup> fillBoxTypeGroup1(BoxGroup boxGroup) {
        List<BoxType> boxTypes = boxTypeRepository.findByCompanyId(createCompany().getId());

        BoxType fbBox = boxTypes.stream().filter(b -> b.getShortName().equals("FB")).findFirst().get();
        BoxType hbBox = boxTypes.stream().filter(b -> b.getShortName().equals("HB")).findFirst().get();
        BoxType qbBox = boxTypes.stream().filter(b -> b.getShortName().equals("QB")).findFirst().get();

        BoxTypeGroup boxTypeGroup1 = new BoxTypeGroup();
        boxTypeGroup1.setQuantity(1);
        boxTypeGroup1.setOrder(0);
        boxTypeGroup1.setBoxType(fbBox);
        boxTypeGroup1.setBoxGroup(boxGroup);

        BoxTypeGroup boxTypeGroup2 = new BoxTypeGroup();
        boxTypeGroup2.setQuantity(2);
        boxTypeGroup2.setOrder(1);
        boxTypeGroup2.setBoxType(hbBox);
        boxTypeGroup2.setBoxGroup(boxGroup);

        BoxTypeGroup boxTypeGroup3 = new BoxTypeGroup();
        boxTypeGroup3.setQuantity(4);
        boxTypeGroup3.setOrder(2);
        boxTypeGroup3.setBoxType(qbBox);
        boxTypeGroup3.setBoxGroup(boxGroup);

        return new HashSet<>(boxTypeGroupRepository.save(Arrays.asList(
            boxTypeGroup1,
            boxTypeGroup2,
            boxTypeGroup3
        )));

    }

    private void fillBankDetails() {
        if(bankDetailsRepositorySH.findOne(1L) == null)
            bankDetailsRepositorySH.save(createBankDetails());
    }

    private void fillAirLines() {
        if(airLinesRepositorySH.findOne(1L) == null)
            airLinesRepositorySH.save(createAirLines());
    }


    private void fillCompany() {
        Company company = createCompany();
        Company existingCompany = companyRepository.findOne(1L);
        if(existingCompany == null) {
            companyRepository.save(company);
        }
    }

    private void fillBoxType() {
        Company company = createCompany();
        if(boxTypeRepository.findOneByCompanyId(1L, company.getId()) == null) {
            boxTypeRepository.save(BoxTypeUtil.getDefaultBoxTypes4Company(company));
        }
    }

    private void fillShippingPolicy() {
        Company company = createCompany();
        if(shippingPolicyRepository.findOneByCompanyId(1L, company.getId()) == null) {
            shippingPolicyRepository.save(ShippingPolicyUtil.getDefaultShippingPolicyForCompany(company));
        }
    }

    private void fillClaimsPolicy() {
        Company company = createCompany();
        if(claimsPolicyRepository.findOneByCompanyId(1L, company.getId()) == null) {
            claimsPolicyRepository.save(ClaimsPolicyUtil.getDefaultClaimsPolicyForCompany(company));
        }
    }

    private void fillPaymentPolicy() {
        Company company = createCompany();
        if(paymentPolicyRepository.findOneByCompanyId(1L, company.getId()) == null) {
            paymentPolicyRepository.save(PaymentPolicyUtil.getDefaultPaymentPolicyForCompany(company));
        }
    }

    private void fillVariety() {
    	if(varietyRepository.findOne(1L) == null) {
    		List<Variety> varieties = createVarieties();
    		for (int i = 0; i < varieties.size(); i++) {
    			varietyRepository.save(varieties.get(i));
    		}
    	}
    }

    private void fillMixType() {
        if(mixTypeRepository.findOne(1L) == null) {
            List<MixType> mixTypeList = createDefaultMixTypes();
            for (int i = 0; i < mixTypeList.size(); i++) {
                mixTypeRepository.save(mixTypeList.get(i));
            }
        }
    }

    private void fillMarketVarieties() {
        if(marketVarietyRepositorySH.findOne(1L) == null) {
            List<MarketVariety> marketVarieties = createMarketVarieties();
            marketVarietyRepositorySH.save(marketVarieties);
        }
    }

    private void fillCompanyUser() {
        Company company = createCompany();
        if (companyUserRepository.findOneWithEagerByCurrentCompanyId(createCompany().getId(), 1L) == null) {
            User user = createUser();
            List<Position> positionList = positionRepository.save(PositionsUtil.getDefaultPositions4Company(company));
            CompanyUser companyUser = createCompanyUser(user, company, positionList);
            companyUserRepository.save(companyUser);
        }
    }

    private void fillBlock() {
    	if(blockRepository.findOne(1L) == null) {
    		List<Block> blocks = createBlocks();
    		for (int i = 0; i < blocks.size(); i++) {
    			blockRepository.save(blocks.get(i));
    		}
    	}
    }

    private void fillTypeOfFlower() {
        if(typeOfFlowerRepository.findOne(1L) == null) {
            List<TypeOfFlower> typeOfFlowers = createTypeOfFlowers();
            for (int i = 0; i < typeOfFlowers.size(); i++) {
                typeOfFlowerRepository.save(typeOfFlowers.get(i));
            }
        }
    }

    private void fillSeasons() {
        List<MarketSeason> marketSeasonList = fillMarketSeasons();
        if (seasonRepository.findOne(1L) == null) {
            List<Season> seasonList = seasonRepository.save(createSeasons());
            for (int i = 0; i < marketSeasonList.size() ; i++) {
                marketSeasonList.get(i).setSeason(seasonList.get(i));
            }
            marketSeasonRepository.save(marketSeasonList);
        }
    }

    private List<MarketSeason> fillMarketSeasons() {
        if (marketSeasonRepository.findOne(1L) == null) {
            return marketSeasonRepository.save(createMarketSeason());
        }
        Long companyId = createCompany().getId();
        return marketSeasonRepository.findAllByCurrentCompany(companyId);
    }


    private void fillClients() {
        if (clientRepository.findOne(1L) == null) {
            List<Client> clientList = clientRepository.save(createClients());
            clientRepository.save(clientList);
        }
    }

    private CompanyUser createCompanyUser(User user, Company company, List<Position> positionList) {
        CompanyUser companyUser = new CompanyUser();

        List<Market> markets = marketRepository.findAllByCurrentCompanyId(createCompany().getId());
        Set<Market> marketSet = new HashSet<>(markets);

        companyUser.setUser(user);
        companyUser.setSkype("skype");
        companyUser.setAccountEmail("vasiliy.pterovich@gmail.com");
        companyUser.setWorkEmail("secondmail@supermail.com");
        companyUser.setCompany(company);
        companyUser.setFullName("Huyushkin");
        companyUser.setWhatsUp("9032435939");
        companyUser.setMarkets(marketSet);
        companyUser.setPositions(positionList.stream().filter(p ->
            p.getName().equals(PositionsUtil.COMPANY_OWNER) || p.getName().equals(PositionsUtil.GENERAL_MANAGER))
            .collect(toSet()));
        companyUser.setOfficePhone("4352 ext.43");
        companyUser.setMobilePhone("9039049447");
        companyUser.setId(1L);
        return companyUser;
    }

    private User createUser() {
        return userService.createUser(
            "vasiliy.pterovich@gmail.com",
            "pass",
            "Petya",
            "Kolotushkin",
            "secondmail@supermail.com",
            null,
            "en",
            true,
            Stream.of(AuthoritiesConstants.COMPANY_OWNER, AuthoritiesConstants.GENERAL_MANAGER)
                .map(authorityRepository::findOne)
                .collect(Collectors.toSet())
        );
    }

    private Company createCompany() {
        Company company = new Company();
        company.setFarmName("Miraflowers");
        company.setLegalName("Legal name");
        company.setCountry("UA");
        company.setCity("Kiev");
        company.setAddress("Khreschatik");
        company.setFarmSize("15");
        company.setGeneralEmailAddress("miraflower@gmail.com");
        company.setGeneralOfficePhone("380937777777");
        company.setId(1L);
        return company;
    }

    private List<BoxType> createBoxTypes() {
        List<BoxType> boxTypes = new ArrayList<>();

        for (long i = 7; i < 25; i++) {
            BoxType boxType = new BoxType();
            boxType.setShortName(generateString(2));
            boxType.setFullName(generateString(5));
            boxType.setCompany(createCompany());
            boxType.setId(i);
            boxTypes.add(boxType);
        }
        return boxTypes;
    }

    private List<Variety> createVarieties() {
        List<TypeOfFlower> typeOfFlowers = createTypeOfFlowers();
    	List<Variety> varieties = new ArrayList<>();
        for (long i = 1; i < 11; i++) {
            Variety variety = new Variety();
            variety.setName(generateString(35));
            variety.setId(i);
            variety.setBreeder(generateString(25));
            variety.setMaxLength(120);
            variety.setMinLength(40);
            variety.setColor(generateString(25));
            variety.setCompany(createCompany());
            variety.setTypeOfFlower(typeOfFlowers.get(0));
            varieties.add(variety);
        }

        for (long i = 11; i < 21; i++) {
            Variety variety = new Variety();
            variety.setName(generateString(35));
            variety.setId(i);
            variety.setBreeder(generateString(25));
            variety.setMaxLength(120);
            variety.setMinLength(40);
            variety.setColor(generateString(25));
            variety.setCompany(createCompany());
            variety.setTypeOfFlower(typeOfFlowers.get(1));
            varieties.add(variety);
        }

        for (long i = 21; i < 31; i++) {
            Variety variety = new Variety();
            variety.setName(generateString(35));
            variety.setId(i);
            variety.setBreeder(generateString(25));
            variety.setMaxLength(120);
            variety.setMinLength(40);
            variety.setColor(generateString(25));
            variety.setCompany(createCompany());
            variety.setTypeOfFlower(typeOfFlowers.get(2));
            varieties.add(variety);
        }
		return varieties;
    }

    private List<Block> createBlocks() {
    	List<Block> blocks = new ArrayList<>();
    	for(long i = 1; i < 4; i++) {
    		Block block = new Block();
    		block.setName("a"+i);
    		block.setBeds((int)i);
    		block.setId(i);
    		block.setVarieties(new HashSet<>(createVarietyForBlock()));
    		block.setCompany(createCompany());
    		blocks.add(block);
    	}
    	return blocks;
    }

    private List<Variety> createVarietyForBlock() {
        List<TypeOfFlower> typeOfFlowers = typeOfFlowerRepository.findAll();
    	List<Variety> varieties = new ArrayList<>();
    	for (long i = 1; i < 11; i++) {
    		Variety variety = new Variety();
    		variety.setName(generateString(35));
    		variety.setId(i);
    		variety.setBreeder(generateString(25));
    		variety.setMaxLength(120);
    		variety.setMinLength(40);
    		variety.setColor(generateString(25));
    		variety.setCompany(createCompany());
    		variety.setTypeOfFlower(typeOfFlowers.get(0));
    		varieties.add(variety);
		}

        for (long i = 11; i < 21; i++) {
            Variety variety = new Variety();
            variety.setName(generateString(35));
            variety.setId(i);
            variety.setBreeder(generateString(25));
            variety.setMaxLength(120);
            variety.setMinLength(40);
            variety.setColor(generateString(25));
            variety.setCompany(createCompany());
            variety.setTypeOfFlower(typeOfFlowers.get(1));
            varieties.add(variety);
        }

        for (long i = 21; i < 31; i++) {
            Variety variety = new Variety();
            variety.setName(generateString(35));
            variety.setId(i);
            variety.setBreeder(generateString(25));
            variety.setMaxLength(120);
            variety.setMinLength(40);
            variety.setColor(generateString(25));
            variety.setCompany(createCompany());
            variety.setTypeOfFlower(typeOfFlowers.get(2));
            varieties.add(variety);
        }
		return varieties;
    }


    private BankDetails createBankDetails(){
        BankDetails bankDetails = new BankDetails();
        bankDetails.setGeneral("General");
        bankDetails.setAlternative("Alternative");
        bankDetails.setId(1L);
        bankDetails.setCompany(companyRepository.findOne(createCompany().getId()));

        return bankDetails;
    }

    private List<TypeOfFlower> createTypeOfFlowers() {
        Company company = createCompany();
        TypeOfFlower roses = new TypeOfFlower();
        roses.setCompany(company);
        roses.setId(1L);
        roses.setName("Roses");

        TypeOfFlower gardenRoses = new TypeOfFlower();
        gardenRoses.setCompany(company);
        gardenRoses.setId(2L);
        gardenRoses.setName("Garden Roses");

        TypeOfFlower sprayRoses = new TypeOfFlower();
        sprayRoses.setCompany(company);
        sprayRoses.setId(3L);
        sprayRoses.setName("Spray Roses");

        return new ArrayList<>(Arrays.asList(roses, gardenRoses, sprayRoses));
    }

    private List<Market> createMarkets() {
        Company company = createCompany();
        Market market = new Market();
        market.setId(1L);
        market.setName("Russian");
        market.setCompany(company);

        Market market2 = new Market();
        market2.setId(2L);
        market2.setName("American");
        market2.setCompany(company);

        Market market3 = new Market();
        market3.setId(3L);
        market3.setName("European");
        market3.setCompany(company);

        return new ArrayList<>(Arrays.asList(market, market2, market3));
    }

    private List<AirLines> createAirLines() {
        List<AirLines> airLinesList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
           AirLines airLines =  new AirLines();
           airLines.setName("name" + i);
           airLines.setNumber(i);
           airLines.setCompany(companyRepository.findOne(createCompany().getId()));
           airLinesList.add(airLines);
        }
        return airLinesList;
    }

    private List<MarketSeason> createMarketSeason() {
        Long companyId = createCompany().getId();
        List<Market> markets = marketRepository.findAllByCurrentCompanyId(companyId);

        MarketSeason marketSeason = new MarketSeason();
        marketSeason.setMarket(markets.get(0));

        MarketSeason marketSeason1 = new MarketSeason();
        marketSeason1.setMarket(markets.get(1));

        MarketSeason marketSeason2 = new MarketSeason();
        marketSeason2.setMarket(markets.get(2));

        List<MarketSeason> marketSeasonList = new ArrayList<>();

        marketSeasonList.add(marketSeason);
        marketSeasonList.add(marketSeason1);
        marketSeasonList.add(marketSeason2);

        return marketSeasonList;
    }

    private List<Season> createSeasons() {
        Long companyId = createCompany().getId();
        List<Position> positions = positionRepository.findAllByCurrentCompany(companyId);
        Set<Position> positionSet = new HashSet<>(positions);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String startDate = "11/09/2017";
        String endDate = "12/09/2017";
        LocalDate localDateStart = LocalDate.parse(startDate, formatter);
        LocalDate localDateEnd = LocalDate.parse(endDate, formatter);
        List<Season> seasons = new ArrayList<>();
        for (int i = 0; i < 3 ; i++) {
            Season season = new Season();
            season.setSeasonName("Verano");
            season.setSeasonYear(2017);
            season.setStartDate(localDateStart);
            season.setEndDate(localDateEnd);
            season.setNotifyStartDate(localDateStart);
            season.setPositions(positionSet);


            seasons.add(season);
        }
        return seasons;
    }

    private List<Client> createClients() {
        Long companyId = createCompany().getId();

        List<ClaimsPolicy> claimsPolicies = claimsPolicyRepository.findAllByCompanyId(companyId);
        List<PaymentPolicy> paymentPolicies = paymentPolicyRepository.findAllByComapnyId(companyId);
        Set<PaymentPolicy> paymentPoliciesSet = new HashSet<>(paymentPolicies);

        List<Client> clients = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Client client = new Client();
            client.setCompanyName("Mosflor" + i);
            client.setCompany(createCompany());
            client.setEmail("mosflor@gmail.com");
            client.setActivationStatus(true);
            client.setAddress("Moscu");
            client.setBlockStatus(false);
            client.setClaimsPolicy(claimsPolicies.get(0));
            client.setIdNumber("43r3r34f34f");
            client.setOfficePhone("2379423747837");
            client.setSkype("fefef");
            client.setWebPage("www.cefe.com");

            clients.add(client);
        }

        return clients;
    }

    private static String generateString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

    private List<MixType> createDefaultMixTypes() {
        List<String> names =  new ArrayList<>(Arrays.asList("Mix color", "Mix red","Mix green","Mix bicolor","Mix pink"
            ,"Mix light-pink","Mix hot-pink","Mix white","Mix yellow","Mix orange","Mix peach", "Mix liliac-lavander",
            "Mix cream","Mix brown","Mix purple"));
        List<MixType> mixTypes =  new ArrayList<>();
        for (String name: names ) {
            MixType mixType = new MixType();
            mixType.setName(name);
            mixType.setCompany(companyRepository.findOne(createCompany().getId()));
            mixTypes.add(mixType);
        }
        return mixTypes;
    }

    private List<MarketVariety> createMarketVarieties() {
        List<MarketVariety> marketVarieties = new ArrayList<>();
        List<Variety> varieties = varietyRepository.findAll();
        marketRepository.findAll().forEach(market -> varieties
            .stream()
            .filter(v -> v.getId() % 3 == 0)
            .forEach(variety -> {
                MarketVariety marketVariety =  new MarketVariety();
                marketVariety.setMarket(market);
                marketVariety.setVariety(variety);
                marketVariety.setType(variety.getId() % 4 == 0 ? MarketVarietyType.PROHIBITED : MarketVarietyType.SPECIAL);
                marketVarieties.add(marketVariety);
        }));
        return marketVarieties;
    }
}

