package com.item.eshop.service.ServiceImpl;

import com.item.eshop.mapper.ProvinceMapper;
import com.item.eshop.mapper.SchoolMapper;
import com.item.eshop.mapper.ShopMapper;
import com.item.eshop.model.Province;
import com.item.eshop.model.School;
import com.item.eshop.model.Shop;
import com.item.eshop.service.ShopClassification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopClassificationImpl implements ShopClassification {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private SchoolMapper schoolMapper;

    public ShopMapper getShopMapper() {
        return shopMapper;
    }

    public void setShopMapper(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    public ProvinceMapper getProvinceMapper() {
        return provinceMapper;
    }

    public void setProvinceMapper(ProvinceMapper provinceMapper) {
        this.provinceMapper = provinceMapper;
    }

    public SchoolMapper getSchoolMapper() {
        return schoolMapper;
    }

    public void setSchoolMapper(SchoolMapper schoolMapper) {
        this.schoolMapper = schoolMapper;
    }

    @Override
    public List<Province> getAllProvice() {
        return provinceMapper.selectAllProvince();
    }

    @Override
    public List<School> getAllSchool() {
        return null;
    }

    @Override
    public List<School> getSchoolByProvice(Integer id) {
        return schoolMapper.selectByProvinceId(id);
    }

    @Override
    public List<Shop> selectShopBySchoolId(Integer id) {
        return shopMapper.selectShopBySchoolId(id);
    }

    @Override
    public List<School> selectSchoolByName(String name) {
        return schoolMapper.selectByName(name);
    }

}
