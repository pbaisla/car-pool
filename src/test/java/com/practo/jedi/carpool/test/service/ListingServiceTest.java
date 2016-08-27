package com.practo.jedi.carpool.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.practo.jedi.carpool.exceptions.EntityNotFoundException;
import com.practo.jedi.carpool.model.AddressModel;
import com.practo.jedi.carpool.model.ListingModel;
import com.practo.jedi.carpool.model.SourceModel;
import com.practo.jedi.carpool.model.UserModel;
import com.practo.jedi.carpool.model.VehicleModel;
import com.practo.jedi.carpool.run.Application;
import com.practo.jedi.carpool.service.ListingService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ListingServiceTest {

  @Autowired
  private ListingService service;

  @Test
  public void testGet() throws EntityNotFoundException {
    ListingModel listing = service.get(1);
    assertNotNull(listing);
    assertEquals(new Integer(1), listing.getUser().getId());
    assertEquals(new Integer(1), listing.getVehicle().getId());
    assertEquals(new Integer(1), listing.getSource().getId());
    assertEquals(new Integer(2), listing.getAddress().getId());
    assertEquals(1, listing.getSeatsAvailable());
  }

  @Test
  public void testCreate() throws EntityNotFoundException {
    ListingModel listing = new ListingModel();
    UserModel user = new UserModel();
    user.setId(1);
    VehicleModel vehicle = new VehicleModel();
    vehicle.setId(1);
    SourceModel source = new SourceModel();
    source.setId(2);
    AddressModel address = new AddressModel();
    address.setId(1);
    address.setLatitude(new BigDecimal("1.11000000"));
    address.setLongitude(new BigDecimal("2.22000000"));
    listing.setUser(user);
    listing.setVehicle(vehicle);
    listing.setSource(source);
    listing.setAddress(address);
    Date now = new Date();
    now.setTime(now.getTime() + 1000);
    listing.setDepartureTime(now);
    listing.setSeatsAvailable(1);
    listing = service.create(listing);

    ListingModel dbListing = service.get(4);
    assertNotNull(dbListing);
    assertEquals(new Integer(1), listing.getUser().getId());
    assertEquals(new Integer(1), listing.getVehicle().getId());
    assertEquals(new Integer(2), listing.getSource().getId());
    assertEquals(new Integer(1), listing.getAddress().getId());
    assertEquals(1, listing.getSeatsAvailable());
    assertEquals(now, listing.getDepartureTime());
  }

  @Test
  public void testUpdate() throws EntityNotFoundException {
    ListingModel listing = service.get(2);
    listing.setSeatsAvailable(4);

    service.update(listing, 2);

    ListingModel dbListing = service.get(2);
    assertNotNull(dbListing);
    assertEquals(4, dbListing.getSeatsAvailable());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testDelete() throws EntityNotFoundException {
    service.delete(3);
    ListingModel listing = service.get(3);
    assertNull(listing);
  }

}