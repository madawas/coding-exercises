import com.acme.serviceavailability.AvailabilityChecker;
import com.acme.serviceavailability.AvailabilityStatus;
import com.acme.serviceavailability.impl.AvailabilityCheckerImpl;
import com.company.onlinestore.AddOn;
import com.company.onlinestore.Basket;
import com.company.onlinestore.Product;
import com.company.onlinestore.ThreeDeeAddOnService;
import com.company.onlinestore.impl.ThreeDeeAddOnServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ThreeDeeAddOnServiceTest {

    private String[] validPostCodes = {"3156", "3224", "6753", "6544"};
    private String[] plannedPostCodes = {"4525", "2416"};
    private String[] unavailablePostCodes = {"7676", "8233", "6754"};
    private ThreeDeeAddOnService threeDeeAddOnService;

    @Before
    public void setup() {
        AvailabilityChecker availabilityChecker = new AvailabilityCheckerImpl();
        this.threeDeeAddOnService = new ThreeDeeAddOnServiceImpl(availabilityChecker);
    }

    /**
     * Testing the scenario where the service is available and the 3D compatible products available in the basket.
     */
    @Test
    public void testServiceAvailableAddOnsCompatibleProductsInBasket() {
        String postcode = validPostCodes[getRandomNumberInRange(0, validPostCodes.length - 1)];
        Basket basket = new Basket();

        basket.getProducts().add(Product.SPORTS);
        basket.getProducts().add(Product.MOVIES_1);
        basket.getProducts().add(Product.KIDS);

        threeDeeAddOnService.checkFor3DAddOnProducts(basket, postcode);

        assertEquals(2, basket.getAddOns().size());
        assertEquals(AvailabilityStatus.SERVICE_AVAILABLE.getDescription(), basket.getNotification());
        assertEquals(new HashSet<>(Arrays.asList(AddOn.SPORTS_3D_ADD_ON, AddOn.MOVIES_3D_ADD_ON)), basket.getAddOns());
    }

    /**
     * Testing the scenario where the service is available and the 3D compatible products unavailable in the basket.
     */
    @Test
    public void testServiceAvailableAddOnsCompatibleProductsNotInBasket() {
        String postcode = validPostCodes[getRandomNumberInRange(0, validPostCodes.length - 1)];
        Basket basket = new Basket();

        basket.getProducts().add(Product.VARIETY);
        basket.getProducts().add(Product.KIDS);

        threeDeeAddOnService.checkFor3DAddOnProducts(basket, postcode);

        assertEquals(0, basket.getAddOns().size());
        assertEquals(AvailabilityStatus.SERVICE_AVAILABLE.getDescription(), basket.getNotification());

    }

    /**
     * Testing the scenario where the service is unavailable and the 3D compatible products available in the basket.
     */
    @Test
    public void testServiceUnavailableCompatibleProductsInBasket() {
        String postcode = unavailablePostCodes[getRandomNumberInRange(0, unavailablePostCodes.length - 1)];
        Basket basket = new Basket();

        basket.getProducts().add(Product.SPORTS);
        basket.getProducts().add(Product.MOVIES_1);
        basket.getProducts().add(Product.KIDS);

        threeDeeAddOnService.checkFor3DAddOnProducts(basket, postcode);

        assertEquals(0, basket.getAddOns().size());
        assertEquals(AvailabilityStatus.SERVICE_UNAVAILABLE.getDescription(), basket.getNotification());
    }

    /**
     * Testing the scenario where the postcode is invalid and the 3D compatible products available in the basket.
     */
    @Test
    public void testServiceInvalidPostCode() {
        String postcode = "9999";
        Basket basket = new Basket();

        basket.getProducts().add(Product.SPORTS);
        basket.getProducts().add(Product.MOVIES_2);
        basket.getProducts().add(Product.KIDS);


        threeDeeAddOnService.checkFor3DAddOnProducts(basket, postcode);

        assertEquals(0, basket.getAddOns().size());
        assertEquals(AvailabilityStatus.POSTCODE_INVALID.getDescription(), basket.getNotification());
    }

    /**
     * Testing the scenario where the service is planned and the 3D compatible products available in the basket.
     */
    @Test
    public void testServicePlannedCompatibleProductsInBasket() {
        String postcode = plannedPostCodes[getRandomNumberInRange(0, plannedPostCodes.length - 1)];
        Basket basket = new Basket();

        basket.getProducts().add(Product.SPORTS);
        basket.getProducts().add(Product.MOVIES_1);
        basket.getProducts().add(Product.KIDS);
        basket.getProducts().add(Product.NEWS);

        threeDeeAddOnService.checkFor3DAddOnProducts(basket, postcode);

        assertEquals(0, basket.getAddOns().size());
        assertEquals(AvailabilityStatus.SERVICE_PLANNED.getDescription(), basket.getNotification());
    }

    /**
     * Generates a random number between given min and max.
     *
     * @param min minimum value
     * @param max maximum value
     * @return random integer between min and max
     */
    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
