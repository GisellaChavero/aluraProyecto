package proyectoMonedas.com;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.Gson;

public class Respuesta {

    private static final String API_KEY = "08554110c0808d9d51a01814"; // Reemplaza con tu clave de API

    // Método para realizar una conversión de moneda
    public void realizarConversion(Scanner scanner) {
        String baseCurrency;
        do {
            System.out.print("Ingrese el código de la moneda que posee (por ejemplo, USD): ");
            baseCurrency = scanner.next().trim().toUpperCase();

            if (baseCurrency.length() != 3) {
                System.out.println("Código de moneda no válido. Debe tener exactamente tres letras (por ejemplo, USD).");
            }
        } while (baseCurrency.length() != 3);

        String targetCurrency;
        do {
            System.out.print("Ingrese el código de la moneda a la que desea convertir (por ejemplo, EUR): ");
            targetCurrency = scanner.next().trim().toUpperCase();

            if (targetCurrency.length() != 3) {
                System.out.println("Código de moneda no válido. Debe tener exactamente tres letras (por ejemplo, EUR).");
            }
        } while (targetCurrency.length() != 3);

        double amount = 0;
        boolean validAmount = false;
        do {
            System.out.print("Ingrese la cantidad de dinero en " + baseCurrency + " que desea convertir: ");
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
                if (amount > 0) {
                    validAmount = true;
                } else {
                    System.out.println("La cantidad debe ser mayor que cero.");
                }
            } else {
                System.out.println("Entrada no válida. Ingrese un número.");
                scanner.next(); // Limpiar la entrada incorrecta
            }
        } while (!validAmount);
        scanner.nextLine();  // Limpiar el buffer

        String url = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", API_KEY, baseCurrency);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                ConvertidorMonedas convertidorMonedas = gson.fromJson(response.body(), ConvertidorMonedas.class);

                if (convertidorMonedas.getConversion_rates().containsKey(targetCurrency)) {
                    double exchangeRate = convertidorMonedas.getConversion_rates().get(targetCurrency);
                    double convertedAmount = amount * exchangeRate;
                    System.out.printf("%.2f %s equivale a %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
                } else {
                    System.out.println("La moneda de destino ingresada no es válida o no está disponible.");
                }
            } else {
                System.out.println("Error en la solicitud: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al realizar la solicitud a la API: " + e.getMessage());
        }
    }

    // Método para mostrar la explicación del programa
    public void mostrarExplicacion() {
        System.out.println("\n=== Explicación del Programa ===");
        System.out.println("Este programa permite convertir una cantidad de dinero de una moneda a otra.");
        System.out.println("Para ello, el usuario debe ingresar el código de la moneda de origen, el código de la moneda de destino, y la cantidad de dinero.");
        System.out.println("Luego, el programa realiza una consulta a una API para obtener la tasa de cambio y muestra el monto equivalente.");
    }
}