package com.impltech.service.util;


import com.impltech.domain.*;
import com.impltech.domain.enumeration.Length;
import com.impltech.service.dto.BoxGroupDTO;
import com.impltech.service.dto.DefaultPriceListDTO;
import com.impltech.service.dto.EmployeeDTO;
import com.impltech.service.dto.SeasonPriceListDTO;
import com.impltech.service.dto.MarketBoxVarietyPropertyDTO;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Created by platon
 */
public class ConvertUtil {

    public static EmployeeDTO companyUserToEmployeeDTO(CompanyUser companyUser) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(companyUser.getId());
        employeeDTO.setEmail(companyUser.getUser().getEmail());
        employeeDTO.setFirstName(companyUser.getUser().getFirstName());
        employeeDTO.setSecondName(companyUser.getUser().getLastName());
        employeeDTO.setPhone(companyUser.getMobilePhone());
        employeeDTO.setSkype(companyUser.getSkype());
        employeeDTO.setWhatsUp(companyUser.getWhatsUp());
        employeeDTO.setPositions(new ArrayList<>(companyUser.getPositions()));
        return employeeDTO;
    }

    public static BoxGroupDTO boxGroupToBoxGroupDTO(BoxGroup boxGroup) {
        if (boxGroup != null) {
            BoxGroupDTO boxGroupDTO = new BoxGroupDTO();
            boxGroupDTO.setId(boxGroup.getId());
            List<BoxType> boxTypes = new ArrayList<>();
            List<Integer> quantities = new ArrayList<>();
            for (BoxTypeGroup boxTypeGroup : boxGroup.getBoxTypeGroups()) {
                boxTypes.add(boxTypeGroup.getBoxType());
                quantities.add(boxTypeGroup.getQuantity());
            }
            boxGroupDTO.setBoxTypes(boxTypes);
            boxGroupDTO.setQuantities(quantities);
            boxGroupDTO.setId(boxGroup.getId());
            return boxGroupDTO;
        }
        return null;
    }

    public static List<DefaultPriceListDTO> listMarketVarietyPropertyPriceListToListPriceListDTO(List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceLists) {
        List<DefaultPriceListDTO> defaultPriceListDTOListtDTOList = new ArrayList<>();

        Set<ShippingPolicy> shippingPolicies = new HashSet<>();
        marketVarietyPropertyPriceLists.forEach((m) -> shippingPolicies.add(m.getShippingPolicy()));

        shippingPolicies.forEach((shippingPolicy) ->  {
            Set<Market> markets = new HashSet<>();
            marketVarietyPropertyPriceLists.stream().filter((mvp) ->  mvp.getShippingPolicy().getId().equals(shippingPolicy.getId()))
                                            .forEach((mvp) -> markets.add(mvp.getMarket()));

            markets.forEach((market) -> {
                List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceListList = new ArrayList<>();
                marketVarietyPropertyPriceLists.stream().filter((mvp) ->
                    mvp.getShippingPolicy().getId().equals(shippingPolicy.getId()) &&  mvp.getMarket().getId().equals(market.getId()))
                    .forEach(marketVarietyPropertyPriceListList::add);

                    Set<Variety> varieties = new HashSet<>();
                    marketVarietyPropertyPriceListList.forEach((v) -> varieties.add(v.getVariety()));

                varieties.forEach((variety) -> { List<MarketVarietyPropertyPriceList> tmp = marketVarietyPropertyPriceListList.stream()
                    .filter((mvp) -> mvp.getVariety().getId().equals(variety.getId())).collect(Collectors.toList());
                    defaultPriceListDTOListtDTOList.add(marketVarietyPropertyPriceListToDefaultPriceListDTO(tmp, market, variety,  shippingPolicy));
                });
            });
        });
        return defaultPriceListDTOListtDTOList;
    }


    public static DefaultPriceListDTO marketVarietyPropertyPriceListToDefaultPriceListDTO(List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceLists, Market market, Variety variety, ShippingPolicy shippingPolicy) {

        DefaultPriceListDTO defaultPriceListDTO = new DefaultPriceListDTO();
        defaultPriceListDTO.setMarket(market);
        defaultPriceListDTO.setVariety(variety);
        defaultPriceListDTO.setShippingPolicy(shippingPolicy);
        defaultPriceListDTO.set_40(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_40")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_50(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_50")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_60(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_60")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_70(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_70")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_80(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_80")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_90(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_90")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_100(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_100")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_110(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_110")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_120(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_120")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_130(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_130")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_140(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_140")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_150(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_150")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_160(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_160")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_170(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_170")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_180(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_180")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_190(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_190")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_200(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_200")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));
        defaultPriceListDTO.set_210(marketVarietyPropertyPriceLists.stream().filter(m -> m.getLength().toString().equals("_210")).map(MarketVarietyPropertyPriceList::getPrice).findAny().orElse(BigDecimal.ZERO));

        return defaultPriceListDTO;
    }

    public static List<MarketVarietyPropertyPriceList> priceListDTOtoMarketVarietyPropertyPriceList(DefaultPriceListDTO defaultPriceListDTO) {
        List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceLists = new ArrayList<>();

        Arrays.stream(Length.values())
            .map(Enum::toString)
            .forEach(length -> {
                MarketVarietyPropertyPriceList marketVarietyPropertyPriceList = setPriceForProperty(length, defaultPriceListDTO);
                if (marketVarietyPropertyPriceList != null) {
                    marketVarietyPropertyPriceLists.add(marketVarietyPropertyPriceList);
                }
            });

        return marketVarietyPropertyPriceLists;
    }

    private static MarketVarietyPropertyPriceList setPriceForProperty(String length, DefaultPriceListDTO defaultPriceListDTO) {
        MarketVarietyPropertyPriceList marketVarietyPropertyPriceList;

        switch (length) {
            case "_40":
                if (defaultPriceListDTO.get_40().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_40());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_50":
                if (defaultPriceListDTO.get_50().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_50());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);

                }
                return null;
            case "_60":
                if (defaultPriceListDTO.get_60().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_60());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);

                }
                return null;
            case "_70":
                if (defaultPriceListDTO.get_70().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_70());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);

                }
                return null;
            case "_80":
                if (defaultPriceListDTO.get_80().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_80());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);

                }
                return null;
            case "_90":
                if (defaultPriceListDTO.get_90().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_90());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_100":
                if (defaultPriceListDTO.get_100().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_100());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_110":
                if (defaultPriceListDTO.get_110().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_110());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_120":
                if (defaultPriceListDTO.get_120().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_120());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_130":
                if (defaultPriceListDTO.get_130().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_130());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_140":
                if (defaultPriceListDTO.get_140().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_140());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_150":
                if (defaultPriceListDTO.get_150().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_150());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_160":
                if (defaultPriceListDTO.get_160().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_160());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_170":
                if (defaultPriceListDTO.get_170().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_170());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_180":
                if (defaultPriceListDTO.get_180().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_180());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_190":
                if (defaultPriceListDTO.get_190().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_190());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_200":
                if (defaultPriceListDTO.get_200().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_200());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
            case "_210":
                if (defaultPriceListDTO.get_210().compareTo(BigDecimal.ZERO) != -1) {
                    marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList();
                    marketVarietyPropertyPriceList.setPrice(defaultPriceListDTO.get_210());
                    return setValueForMarketVarietyPropertyPriceList(marketVarietyPropertyPriceList,length,defaultPriceListDTO);
                }
                return null;
        }
        return null;
    }

    private static MarketVarietyPropertyPriceList setValueForMarketVarietyPropertyPriceList(MarketVarietyPropertyPriceList marketVarietyPropertyPriceList, String length, DefaultPriceListDTO defaultPriceListDTO) {
        marketVarietyPropertyPriceList.setLength(Length.valueOf(length));
        marketVarietyPropertyPriceList.setVariety(defaultPriceListDTO.getVariety());
        marketVarietyPropertyPriceList.setMarket(defaultPriceListDTO.getMarket());
        marketVarietyPropertyPriceList.setPriceList(defaultPriceListDTO.getPriceList());
        marketVarietyPropertyPriceList.setShippingPolicy(defaultPriceListDTO.getShippingPolicy());
        return marketVarietyPropertyPriceList;
    }


    public static SeasonPriceListDTO marketSeasonVarietyPropertiesToSeasonPriceListDTO (List<MarketSeasonVarietyProperty> marketSeasonVarietyProperties, MarketSeason marketSeason, Variety variety, ShippingPolicy shippingPolicy) {

        SeasonPriceListDTO seasonPriceListDTO = new SeasonPriceListDTO();
        seasonPriceListDTO.setMarketSeason(marketSeason);
        seasonPriceListDTO.setVariety(variety);
        seasonPriceListDTO.setShippingPolicy(shippingPolicy);
        seasonPriceListDTO.set_40(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_40")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_50(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_50")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_60(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_60")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_70(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_70")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_80(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_80")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_90(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_90")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_100(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_100")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_110(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_110")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_120(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_120")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_130(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_130")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_140(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_140")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_150(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_150")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_160(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_160")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_170(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_170")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_180(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_180")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_190(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_190")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_200(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_200")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));
        seasonPriceListDTO.set_210(marketSeasonVarietyProperties.stream().filter(m -> m.getLength().toString().equals("_210")).map(MarketSeasonVarietyProperty::getPrice).findAny().orElse(BigDecimal.ZERO));

        return seasonPriceListDTO;
    }




    public static List<MarketSeasonVarietyProperty> seasonPriceListDTOtoMarketSeasonVarietyProperty(SeasonPriceListDTO seasonPriceListDTO) {
        List<MarketSeasonVarietyProperty> marketSeasonVarietyProperties = new ArrayList<>();

        Arrays.stream(Length.values())
            .map(Enum::toString)
            .forEach(length -> {
                MarketSeasonVarietyProperty marketSeasonVarietyProperty = setPriceForProperty(length, seasonPriceListDTO);
                if (marketSeasonVarietyProperty != null) {
                    marketSeasonVarietyProperties.add(marketSeasonVarietyProperty);
                }
            });

        return marketSeasonVarietyProperties;
    }

    private static MarketSeasonVarietyProperty setPriceForProperty(String length, SeasonPriceListDTO seasonPriceListDTO) {
        MarketSeasonVarietyProperty marketSeasonVarietyProperty;

        switch (length) {
            case "_40":
                if (seasonPriceListDTO.get_40().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_40());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_50":
                if (seasonPriceListDTO.get_50().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_50());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);

                }
                return null;
            case "_60":
                if (seasonPriceListDTO.get_60().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_60());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);

                }
                return null;
            case "_70":
                if (seasonPriceListDTO.get_70().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_70());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);

                }
                return null;
            case "_80":
                if (seasonPriceListDTO.get_80().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_80());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);

                }
                return null;
            case "_90":
                if (seasonPriceListDTO.get_90().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_90());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_100":
                if (seasonPriceListDTO.get_100().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_100());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_110":
                if (seasonPriceListDTO.get_110().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_110());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_120":
                if (seasonPriceListDTO.get_120().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_120());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_130":
                if (seasonPriceListDTO.get_130().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_130());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_140":
                if (seasonPriceListDTO.get_140().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_140());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_150":
                if (seasonPriceListDTO.get_150().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_150());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_160":
                if (seasonPriceListDTO.get_160().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_160());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_170":
                if (seasonPriceListDTO.get_170().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_170());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_180":
                if (seasonPriceListDTO.get_180().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_180());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_190":
                if (seasonPriceListDTO.get_190().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_190());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_200":
                if (seasonPriceListDTO.get_200().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_200());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
            case "_210":
                if (seasonPriceListDTO.get_210().compareTo(BigDecimal.ZERO) != -1) {
                    marketSeasonVarietyProperty = new MarketSeasonVarietyProperty();
                    marketSeasonVarietyProperty.setPrice(seasonPriceListDTO.get_210());
                    return setValueForMarketSeasonVarietyProperty(marketSeasonVarietyProperty,length,seasonPriceListDTO);
                }
                return null;
        }
        return null;
    }

    private static MarketSeasonVarietyProperty setValueForMarketSeasonVarietyProperty(MarketSeasonVarietyProperty marketSeasonVarietyProperty, String length, SeasonPriceListDTO seasonPriceListDTO) {
        marketSeasonVarietyProperty.setLength(Length.valueOf(length));
        marketSeasonVarietyProperty.setVariety(seasonPriceListDTO.getVariety());
        marketSeasonVarietyProperty.setMarketSeason(seasonPriceListDTO.getMarketSeason());
        marketSeasonVarietyProperty.setPriceList(seasonPriceListDTO.getPriceList());
        marketSeasonVarietyProperty.setShippingPolicy(seasonPriceListDTO.getShippingPolicy());
        return marketSeasonVarietyProperty;
    }

    public static List<SeasonPriceListDTO> listMarketSeasonVarietyPropertyToListSeasonPriceListDTO(List<MarketSeasonVarietyProperty> marketSeasonVarietyProperties) {
        List<SeasonPriceListDTO> seasonPriceListDTOList = new ArrayList<>();

        Set<ShippingPolicy> shippingPolicies = new HashSet<>();
        marketSeasonVarietyProperties.forEach((m) -> shippingPolicies.add(m.getShippingPolicy()));

        shippingPolicies.forEach((shippingPolicy) ->  {
            Set<MarketSeason> marketSeasons = new HashSet<>();
            marketSeasonVarietyProperties.stream().filter((mvp) ->  mvp.getShippingPolicy().getId().equals(shippingPolicy.getId()))
                .forEach((mvp) -> marketSeasons.add(mvp.getMarketSeason()));

            marketSeasons.forEach((marketSeason) -> {
                List<MarketSeasonVarietyProperty> marketSeasonVarietyPropertyList = new ArrayList<>();
                marketSeasonVarietyProperties.stream().filter((mvp) ->
                    mvp.getShippingPolicy().getId().equals(shippingPolicy.getId()) &&  mvp.getMarketSeason().getId().equals(marketSeason.getId()))
                    .forEach(marketSeasonVarietyPropertyList::add);

                Set<Variety> varieties = new HashSet<>();
                marketSeasonVarietyPropertyList.forEach((v) -> varieties.add(v.getVariety()));

                varieties.forEach((variety) -> { List<MarketSeasonVarietyProperty> tmp = marketSeasonVarietyPropertyList.stream()
                    .filter((mvp) -> mvp.getVariety().getId().equals(variety.getId())).collect(Collectors.toList());
                    seasonPriceListDTOList.add(marketSeasonVarietyPropertiesToSeasonPriceListDTO(tmp,marketSeason , variety,  shippingPolicy));
                });
            });
        });
        return seasonPriceListDTOList;
    }


    public static List<DefaultPriceListDTO> listMarketVarietyPropertyPriceListToListPriceListDTO(
        List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceLists,
        List<Variety> varieties, Market market,
        ShippingPolicy shippingPolicy) {

        List<DefaultPriceListDTO> defaultPriceListDTOList = new ArrayList<>();
        varieties.forEach(variety ->  {
            List<MarketVarietyPropertyPriceList> tmp =  new ArrayList<>();
            marketVarietyPropertyPriceLists.stream().filter((mvp) -> mvp.getVariety().getId().equals(variety.getId()))
                .forEach(tmp::add);
            defaultPriceListDTOList.add(marketVarietyPropertyPriceListToDefaultPriceListDTO(tmp, market, variety, shippingPolicy));
        });
        return defaultPriceListDTOList;
    }



    public static List<SeasonPriceListDTO> listMarketSeasonVarietyPropertyToListSeasonPriceListDTO(
        List<MarketSeasonVarietyProperty> marketSeasonVarietyProperties,
        List<Variety> varieties,
        MarketSeason marketSeason,
        ShippingPolicy shippingPolicy) {

        List<SeasonPriceListDTO> seasonPriceListDTOS = new ArrayList<>();
        varieties.forEach(variety ->  {
            List<MarketSeasonVarietyProperty> tmp =  new ArrayList<>();
            marketSeasonVarietyProperties.stream().filter((mvp) -> mvp.getVariety().getId().equals(variety.getId()))
                .forEach(tmp::add);
            seasonPriceListDTOS.add(marketSeasonVarietyPropertiesToSeasonPriceListDTO(tmp, marketSeason, variety, shippingPolicy));
        });
        return seasonPriceListDTOS;
    }

    public static List<MarketBoxVarietyPropertyDTO> marketBoxVarietyPropertiesToDTO(List<MarketBoxVarietyProperty> properties) {
        return Optional.ofNullable(properties)
            .map(ps -> ps
            .stream()
            .map(el -> ps
                .stream()
                .filter(elem -> elem.getVariety().getId().equals(el.getVariety().getId()))
                .collect(Collectors.groupingBy(MarketBoxVarietyProperty::getVariety))
                .entrySet().stream().collect(toMap(
                    Map.Entry::getKey,
                    k -> k.getValue().stream()
                        .collect(toMap(
                            (MarketBoxVarietyProperty mbvp) ->
                                new AbstractMap.SimpleEntry<>(mbvp.getLength(), mbvp.getCapacity()),
                            MarketBoxVarietyProperty::getId))))
            ).distinct()
            .flatMap(vMap ->
                vMap.entrySet().stream().map(e -> {
                    MarketBoxVarietyPropertyDTO dto = new MarketBoxVarietyPropertyDTO();
                    dto.setVariety(e.getKey());
                    dto.setCapacitiesOnLength(e.getValue());
                    dto.setMarketBox(properties.get(0).getMarketBox());
                    return dto;
                }))
            .collect(toList())).orElse(null);
    }

    public static List<MarketBoxVarietyProperty> dtoToMarketBoxVarietyProperties(List<MarketBoxVarietyPropertyDTO> dtos) {
        return dtos.stream()
            .map(dto -> ((Map<String, Object>)dto.getCapacitiesOnLengthObj())
                .entrySet()
                .stream()
                .map(e -> {
                    MarketBoxVarietyProperty property = new MarketBoxVarietyProperty();
                    String[] parts = e.getKey().split(",");
                    property
                        .length(Length.values()[Integer.parseInt( parts[0])])
                        .capacity(Integer.parseInt(parts[1]))
                        .variety(dto.getVariety())
                        .marketBox(dto.getMarketBox())
                        .id(Long.parseLong(String.valueOf(e.getValue())));
                    return property;
                }).collect(toList()))
            .flatMap(list -> list.stream().map(el -> (MarketBoxVarietyProperty)el))
            .collect(toList());
    }

}
