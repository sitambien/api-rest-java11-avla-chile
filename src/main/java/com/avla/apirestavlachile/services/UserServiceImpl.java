package com.avla.apirestavlachile.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avla.apirestavlachile.dao.UserDao;
import com.avla.apirestavlachile.entities.User;



@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public User save(User user) {
        validateUserData(user);
    	return userDao.save(user);
    }

    @Override
    @Transactional()
    public void delete(Long id) {
        userDao.deleteById(id);
    }

    // Validaciones
    private void validateUserData(User user) {
        // Validar el correo electrónico
        Optional<User> usuarioUpdate = userDao.findByEmail(user.getEmail());
        if (usuarioUpdate.isPresent()) {
            if (user.getId() == null || !usuarioUpdate.get().getId().equals(user.getId())) {
                throw new IllegalArgumentException("El correo ya se encuentra registrado");
            }
        }

        // Validación del correo por una expresión regular.
        if(!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) { // https://www.baeldung.com/java-email-validation-regex
            throw new IllegalArgumentException("El correo no tiene el formato adecuado");
        }

        // Validación de la contraseña por una expresión regular.
        if(!user.getPassword().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]{2}).{6,}$")) { // https://www.baeldung.com/java-password-validation-regex
            throw new IllegalArgumentException("La contraseña no tiene el formato adecuado: Una mayúscula, letras minúsculas, y dos números");
        }
    }

}
