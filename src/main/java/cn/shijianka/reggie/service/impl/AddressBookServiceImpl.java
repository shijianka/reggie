package cn.shijianka.reggie.service.impl;

import cn.shijianka.reggie.entity.AddressBook;
import cn.shijianka.reggie.mapper.AddressBookMapper;
import cn.shijianka.reggie.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>implements AddressBookService {
}
