package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IUserService;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Msg;
import dev.patika.ecommerce.dao.UserRepository;
import dev.patika.ecommerce.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User get(int id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Page<User> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User update(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        User user = this.get(id);
        this.userRepository.delete(user);
        return true;
    }
}
