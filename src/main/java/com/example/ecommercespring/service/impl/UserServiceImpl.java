package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.UserDTO;
import com.example.ecommercespring.entity.Brand;
import com.example.ecommercespring.entity.Role;
import com.example.ecommercespring.entity.Shipper;
import com.example.ecommercespring.entity.User;
import com.example.ecommercespring.repository.UserRepository;
import com.example.ecommercespring.repository.RoleRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<UserDTO> getUserListByRole(Long roleId) {
        List<User> list = userRepository.findAllByRole_Id(roleId);
        return userRepository.findAllByRole_Id(roleId).stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getEmployeeById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return ResponseEntity
                    .badRequest()
                    .body(new Response(false, "Nhân viên không tồn tại"));
        UserDTO userDTO = new UserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @Override
    public Response addNewCustomer(UserDTO userDTO) {
        if (userDTO.getPhoneNumber() == null || userDTO.getEmail() == null) {
            return new Response(false, "Số điện thoại hoặc email rỗng");
        }
        User checkUser = userRepository.findByPhoneNumberAndEmail(userDTO.getPhoneNumber(), userDTO.getEmail());
        if (checkUser != null) {
            return new Response(false, "Số điện thoại hoặc email đã tồn tại");
        }

        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        if (userDTO.getRole().name() != "ROLE_USER") {
            return new Response(false, "Wrong role name");
        }
        Role role = roleRepository.getByName(userDTO.getRole());
        User user = userDTO.toEntity();
        user.setRole(role);
        userRepository.save(user);
        return new Response(true, "Tạo tài khoản thành công");
    }

    @Override
    public Response addNewEmployee(UserDTO userDTO) {
        if (userDTO.getPhoneNumber() == null || userDTO.getEmail() == null) {
            return new Response(false, "Số điện thoại hoặc email rỗng");
        }
        if (userDTO.getPhoneNumber() == null || userDTO.getEmail() == null) {
            return new Response(false, "Số điện thoại hoặc email rỗng");
        }

        User checkUserPN = userRepository.findByPhoneNumber(userDTO.getPhoneNumber());
        if (checkUserPN != null) {
            return new Response(false, "Số điện thoại đã tồn tại");
        }

        Optional<Object> checkUser = userRepository.findByEmail(userDTO.getEmail());
        if (checkUser != null) {
            return new Response(false, "Email đã tồn tại");
        }

        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        if (userDTO.getRole().name() != "ROLE_ADMIN") {
            return new Response(false, "Wrong role name");
        }
        Role role = roleRepository.getByName(userDTO.getRole());
        User user = userDTO.toEntity();
        user.setRole(role);
        userRepository.save(user);
        return new Response(true, "Tạo tài khoản thành công");
    }

    @Override
    public Response deleteEmployee(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null || user.getRole().getId() == 2)
            return new Response(false, "Nhân viên không tồn tại");

        if (user.getPromotionList().size() > 0)
            return new Response(false, "Nhân viên đã tạo đợt khuyến mãi không thể xóa");
        if (user.getOrderUserList().size() > 0)
            return new Response(false, "Nhân viên đã duyệt phiếu đặt không thể xóa");
        if (user.getOrderSupplyList().size() > 0)
            return new Response(false, "Nhân viên đã tạo đơn đặt hàng từ hãng không thể xóa");
        if (user.getReceiptList().size() > 0)
            return new Response(false, "Nhân viên đã tạo phiếu nhập không thể xóa");
        if (user.getInvoiceList().size() > 0)
            return new Response(false, "Nhân viên đã tạo hóa đơn không thể xóa");

        userRepository.deleteById(id);
        return new Response(true, "Xóa nhân viên thành công");

    }


    @Override
    public Response modify(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElse(null);
        if (user == null)
            return new Response(false, "Khách hàng không tồn tại");


        if (userDTO.getPhoneNumber() == null || userDTO.getEmail() == null) {
            return new Response(false, "Số điện thoại hoặc email rỗng");
        }
        User checkUserPN = userRepository.findByPhoneNumber(userDTO.getPhoneNumber());
        if (checkUserPN != null) {
            return new Response(false, "Số điện thoại đã tồn tại");
        }
        Optional<Object> checkUser = userRepository.findByEmail(userDTO.getEmail());
        if (checkUser != null) {
            return new Response(false, "Email đã tồn tại");
        }

        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFullName(userDTO.getFullName());
        userRepository.save(user);
        return new Response(true, "Sửa thông tin thành công");
    }
}
