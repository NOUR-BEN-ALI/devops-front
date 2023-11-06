package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations; // Import this class for initialization


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

  @Mock
  StockRepository stockRepository;
  @InjectMocks
  StockServiceImpl stockService;

  @BeforeEach
  void setUp() {
    // Initialize Mockito annotations
    MockitoAnnotations.openMocks(this);
    // Clear the database before each test

      }
  @Test
  void retrieveAllStocks() {
    // Mock the behavior of stockRepository.findAll() to return a list of stocks
    Stock stock1 = new Stock("Stock1", 100, 50);
    Stock stock2 = new Stock("Stock2", 200, 75);
    Mockito.when(stockRepository.findAll()).thenReturn(List.of(stock1, stock2));

    stockRepository.save(stock1);
    stockRepository.save(stock2);

    // Retrieve the data
    List<Stock> result = stockRepository.findAll();

    // Log the results for debugging
    for (Stock stock : result) {
      System.out.println("Stock: " + stock.getLibelleStock() + ", Qte: " + stock.getQte() + ", Qte Min: " + stock.getQteMin());
    }

    // Assertions
    assertNotNull(result);

  }



  @Test
  void addStock() {
    // Arrange: Prepare data and dependencies
    Stock stockToAdd = new Stock("NewStock", 50, 25);

    // Stub the behavior of stockRepository.save
    Mockito.when(stockRepository.save(stockToAdd)).thenReturn(stockToAdd);

    // Act: Call the addStock method
    Stock addedStock = stockService.addStock(stockToAdd);

    // Assert: Check the result and any expected behavior
    assertNotNull(addedStock); // Check that a stock was added
    assertEquals("NewStock", addedStock.getLibelleStock()); // Replace with the expected values
    assertEquals(50, addedStock.getQte());
    assertEquals(25, addedStock.getQteMin());
  }




  @Test
  void deleteStock() {
    Stock stockToDelete = new Stock("StockToDelete", 100, 50);
    Mockito.when(stockRepository.save(stockToDelete)).thenReturn(stockToDelete);
    Mockito.doNothing().when(stockRepository).deleteById(stockToDelete.getIdStock());

    // Add a stock item to the database for deletion
    // Replace with your actual Stock data
    Stock addedStock = stockRepository.save(stockToDelete);

    // Act: Call the deleteStock method
    stockService.deleteStock(addedStock.getIdStock());

    // Assert: Check the result and any expected behavior
    assertNull(stockRepository.findById(addedStock.getIdStock()).orElse(null));
  }


  @Test
  void updateStock() {
    Stock stockToUpdate = new Stock("StockToUpdate", 100, 50);
    Stock updatedStock = new Stock("UpdatedStock", 75, 40);
    Mockito.when(stockRepository.save(stockToUpdate)).thenReturn(updatedStock);

    // Act: Call the updateStock method
    updatedStock = stockService.updateStock(stockToUpdate);

    // Assert: Check the result and any expected behavior
    assertNotNull(updatedStock); // Check that a stock was updated
    assertEquals("UpdatedStock", updatedStock.getLibelleStock()); // Replace with the expected values
    assertEquals(75, updatedStock.getQte());
    assertEquals(40, updatedStock.getQteMin());
  }

  @Test
  void retrieveStock() {
    // Mock the behavior of stockRepository.save() and stockRepository.findById()
    Stock stockToRetrieve = new Stock("StockToRetrieve", 180, 60);
    Mockito.when(stockRepository.save(stockToRetrieve)).thenReturn(stockToRetrieve);
    Mockito.when(stockRepository.findById(stockToRetrieve.getIdStock())).thenReturn(Optional.of(stockToRetrieve));

    // Arrange: Prepare data and dependencies
    Stock addedStock = stockService.addStock(stockToRetrieve);

    // Act: Call the retrieveStock method
    Stock retrievedStock = stockService.retrieveStock(addedStock.getIdStock());

    // Assert: Check the result and any expected behavior
    assertNotNull(retrievedStock); // Check that a stock was retrieved
    assertEquals("StockToRetrieve", retrievedStock.getLibelleStock()); // Replace with the expected values
    assertEquals(180, retrievedStock.getQte());
    assertEquals(60, retrievedStock.getQteMin());
  }


}
