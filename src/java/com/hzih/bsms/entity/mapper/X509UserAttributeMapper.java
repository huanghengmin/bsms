package com.hzih.bsms.entity.mapper;

import com.hzih.bsms.domain.CaUser;
import com.hzih.bsms.entity.X509User;

import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

/**
 * Created with IntelliJ IDEA.
 * User: hhm
 * Date: 14-5-2
 * Time: 下午4:46
 * To change this template use File | Settings | File Templates.
 */
public class X509UserAttributeMapper {

    public static CaUser mapFromAttributes(SearchResult sr) throws javax.naming.NamingException {
        CaUser caUser = new CaUser();

        Attributes attr = sr.getAttributes();

        caUser.setCn((String) attr.get(X509User.getCnAttr()).get());

//        caUser.setDn(sr.getNameInNamespace());

       /* if (attr.get(X509User.getIdCardAttr()) != null) {
            caUser.setHzihid((String) attr.get(X509User.getIdCardAttr()).get());
        }
        if (attr.get(X509User.getPhoneAttr()) != null) {
            caUser.setHzihphone((String) attr.get(X509User.getPhoneAttr()).get());
        }
        if (attr.get(X509User.getAddressAttr()) != null) {
            caUser.setHzihaddress((String) attr.get(X509User.getAddressAttr()).get());
        }
        if (attr.get(X509User.getUserEmailAttr()) != null) {
            caUser.setHzihemail((String) attr.get(X509User.getUserEmailAttr()).get());
        }*/
        /*if (attr.get(X509User.getEmployeeCodeAttr()) != null) {
            caUser.setEmployeeCode((String) attr.get(X509User.getEmployeeCodeAttr()).get());
        }*/
        /*if (attr.get(caUser.getOrgcodeAttr()) != null) {
            caUser.setOrgCode((String)attr.get(caUser.getOrgcodeAttr()).get());
        }
        if (attr.get(caUser.getPwdAttr()) != null) {
            caUser.setPwd((String)attr.get(caUser.getPwdAttr()).get());
        }*/
        /*if (attr.get(X509User.getCertStatusAttr()) != null) {
            caUser.setHzihcastatus((String)attr.get(X509User.getCertStatusAttr()).get());
        }
        if (attr.get(X509User.getSerialAttr()) != null) {
            caUser.setHzihcaserialNumber((String)attr.get(X509User.getSerialAttr()).get());
        }*/
       /* if (attr.get(X509User.getCreateDateAttr()) != null) {
            caUser.setCreateDate((String)attr.get(X509User.getCreateDateAttr()).get());
        }
        if (attr.get(X509User.getEndDateAttr()) != null) {
            caUser.setEndDate((String)attr.get(X509User.getEndDateAttr()).get());
        }
        if (attr.get(X509User.getIssueCaAttr()) != null) {
            caUser.setIssueCa((String)attr.get(X509User.getIssueCaAttr()).get());
        }*/
        /*if (attr.get(X509User.getCertTypeAttr()) != null) {
            caUser.setHzihcertificatetype((String)attr.get(X509User.getCertTypeAttr()).get());
        }*/
        /*if (attr.get(X509User.getKeyLengthAttr()) != null) {
            caUser.setKeyLength((String)attr.get(X509User.getKeyLengthAttr()).get());
        }*/
       /* if (attr.get(X509User.getValidityAttr()) != null) {
            caUser.setHzihcavalidity((String)attr.get(X509User.getValidityAttr()).get());
        }
        if (attr.get(X509User.getProvinceAttr()) != null) {
            caUser.setHzihprovince((String)attr.get(X509User.getProvinceAttr()).get());
        }
        if (attr.get(X509User.getCityAttr()) != null) {
            caUser.setHzihcity((String)attr.get(X509User.getCityAttr()).get());
        }
        if (attr.get(X509User.getOrganizationAttr()) != null) {
            caUser.setHzihorganization((String)attr.get(X509User.getOrganizationAttr()).get());
        }
        if (attr.get(X509User.getInstitutionAttr()) != null) {
            caUser.setHzihinstitutions((String)attr.get(X509User.getInstitutionAttr()).get());
        }*/
        /*if (attr.get(X509User.getDescAttr()) != null) {
            caUser.setDesc((String)attr.get(X509User.getDescAttr()).get());
        }*/
        return caUser;
    }
}
