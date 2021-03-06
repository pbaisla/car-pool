package com.practo.jedi.carpool.service;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practo.jedi.carpool.data.entity.User;
import com.practo.jedi.carpool.data.repository.UserRepository;
import com.practo.jedi.carpool.exceptions.EntityNotFoundException;
import com.practo.jedi.carpool.model.UserModel;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);


  @Autowired
  private UserRepository repository;

  @Override
  public Iterable<UserModel> get() {
    Iterable<User> entities = repository.findAll();
    ArrayList<UserModel> models = new ArrayList<UserModel>();
    for (User entity : entities) {
      UserModel model = new UserModel();
      try {
        model.fromEntity(entity);
        models.add(model);

      } catch (EntityNotFoundException err) {
        LOG.error(err);
      }
    }
    return models;
  }


  @Override
  public UserModel get(Integer id) throws EntityNotFoundException {
    User entity = repository.findOne(id);
    UserModel model = new UserModel();
    model.fromEntity(entity);
    return model;
  }


  @Override
  public UserModel create(UserModel user) throws EntityNotFoundException {
    User entity = user.toEntity();
    entity.setCreatedAt(new Date());
    entity = repository.save(entity);
    try {
      user.fromEntity(entity);
    } catch (EntityNotFoundException err) {
      LOG.error(err);
    }
    return user;
  }

  @Override
  public UserModel update(UserModel user, Integer id) throws EntityNotFoundException {
    user.setId(id);
    User entity = user.toEntity();
    entity.setModifiedAt(new Date());
    entity = repository.save(entity);
    try {
      user.fromEntity(entity);
    } catch (EntityNotFoundException err) {
      LOG.error(err);
    }
    return user;
  }

  @Override
  public void delete(Integer id) throws EntityNotFoundException {
    repository.delete(id);
  }

  @Override
  public UserModel findByEmail(String email) {
    UserModel model = new UserModel();
    User entity = repository.findByEmail(email);
    try {
      model.fromEntity(entity);
      return model;
    } catch (EntityNotFoundException err) {
      return null;
    }
  }

}
