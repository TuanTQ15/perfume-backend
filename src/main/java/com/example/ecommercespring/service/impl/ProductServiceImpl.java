package com.example.ecommercespring.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.ecommercespring.dto.ProductDTO;
import com.example.ecommercespring.dto.ProductSearchDTO;
import com.example.ecommercespring.entity.Brand;

import com.example.ecommercespring.entity.Product;
import com.example.ecommercespring.repository.BrandRepository;

import com.example.ecommercespring.repository.DetailReceiptRepository;
import com.example.ecommercespring.repository.ProductRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "ptithcm",
            "api_key", "649875216812692",
            "api_secret", "JGU8KM7qbRLHeM86XAT_HG5XQCA"));

    @Autowired
    ProductRepository productRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    DetailReceiptRepository detailReceiptRepository;


    @Override
    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream().map(ProductDTO::new)
                .sorted(Comparator.comparing(o -> o.getQuantityInStock()))
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductSearchDTO> getAllForSelect() {
        List<ProductDTO> productDTOList =productRepository.findAll().stream().map(ProductDTO::new)
                .sorted(Comparator.comparing(o -> o.getQuantityInStock()))
                .collect(Collectors.toList());
        List < ProductSearchDTO> productSearchDTOList= new ArrayList<>();
        for (ProductDTO product :productDTOList) {
            ProductSearchDTO productSearch = new ProductSearchDTO(product.getProductId(),product.getProductName());
            productSearchDTOList.add(productSearch);
        }
        return productSearchDTOList;
    }
    @Override
    public List<ProductDTO> getAllDiscount() {
        return productRepository.findAll().stream()
                .map(ProductDTO::new)
                .filter(productDTO -> productDTO.getDetailPromotion() != null).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllNewProduct() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        cal.add(Calendar.DATE, -14); //minus number would decrement the days\
        List<Long> productIdList = detailReceiptRepository.findAll().stream()
                .filter(detailReceipt -> {
                    return detailReceipt.getReceipt().getCreatedDate().compareTo(cal.getTime()) > 0;
                })
                .map(detailReceipt -> detailReceipt.getProduct().getProductId())
                .distinct()
                .collect(Collectors.toList());

        return productRepository.findAll().stream()
                .map(ProductDTO::new)
                .filter(productDTO -> productIdList.contains(productDTO.getProductId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllHotSell() {
        return productRepository.findAll().stream()
                .map(ProductDTO::new)
                .filter(product -> product.getQuantityInStock() >0)
                .sorted(Comparator.comparing(ProductDTO::getQuantitySold).reversed())
                .limit(6)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getByBrand(Long brandId) {
        return productRepository.findAll().stream()
                .filter(product -> product.getBrand().getBrandId() == brandId)
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getByid(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().body(new Response(false,"S???n ph???m kh??ng t???n t???i"));
        }
        return ResponseEntity.ok(new ProductDTO(product));
    }

    @Override
    public Response addNew(ProductDTO productDTO) {
        if (productDTO == null || productDTO.getBrandId() == null) {
            return new Response(false, "D??? li???u tr???ng");
        }
        Brand brand = brandRepository.findById(productDTO.getBrandId()).orElse(null);
        if (brand == null) {
            return new Response(false, "M?? h??ng kh??ng t???n t???i");
        }
        if(productDTO.getDescription().length()>255){
            return new Response(false, "M?? t??? ph???i d?????i 255 k?? t???");
        }

        Product product = productDTO.toEntity();
        product.setBrand(brand);

        try {
            Map uploadResult = cloudinary.uploader().upload(productDTO.getImage(), ObjectUtils.emptyMap());
            product.setImage(uploadResult.get("url").toString());
        } catch (IOException ex) {
            return new Response(false, "Upload img failed");
        }
        System.out.println(product);
        productRepository.save(product);
        return new Response(true, "Th??m s???n ph???m th??nh c??ng");
    }

    @Override
    public Response modify(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getProductId()).orElse(null);
        if (product.getBrand().getBrandId() != productDTO.getBrandId()) {
            Brand brand = brandRepository.findById(productDTO.getBrandId()).orElse(null);
            if (brand == null)
                return new Response(false, "H??ng kh??ng t???n t???i");
            product.setBrand(brand);
        }


        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setProductName(productDTO.getProductName());
        product.setQuantityInStock(productDTO.getQuantityInStock());
        if (product.getImage() != productDTO.getImage()) {
            try {
                Map uploadResult = cloudinary.uploader().upload(productDTO.getImage(), ObjectUtils.emptyMap());
                product.setImage(uploadResult.get("url").toString());
            } catch (IOException ex) {
                return new Response(false, "Upload img failed");
            }
        }

        productRepository.save(product);
        return new Response(true, "S???a s???n ph???m th??nh c??ng");

    }

    @Override
    public Response delete(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return new Response(false, "S???n ph???m kh??ng t???n t???i");
        }
        if (product.getOrderUserList().size() > 0) {
            return new Response(true, "S???n ph???m ???? c?? ????n ?????t h??ng kh??ng th??? x??a");
        }
        if (product.getPromotionList().size() > 0) {
            return new Response(true, "S???n ph???m ???? c?? ?????t khuy???n m??i kh??ng th??? x??a");
        }
        if (product.getReceiptList().size() > 0) {
            return new Response(true, "S???n ph???m ???? c?? phi???u nh???p kh??ng th??? x??a");
        }
        if (product.getOrderSupplyList().size() > 0) {
            return new Response(true, "S???n ph???m ???? c?? ????n ?????t h??ng t??? h??ng");
        }
        productRepository.deleteById(id);

        return new Response(true, "X??a s???n ph???m th??nh c??ng");
    }
}
