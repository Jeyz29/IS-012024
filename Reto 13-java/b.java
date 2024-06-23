import java.io.*;
import java.util.*;

class Equipo {
    String descripcion;
    int cantidad;
    double costoUnitario;
    String fechaAdquisicion;
    String numeroFactura;
    String ciResponsable;

    public Equipo(String descripcion, int cantidad, double costoUnitario, String fechaAdquisicion, String numeroFactura, String ciResponsable) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.fechaAdquisicion = fechaAdquisicion;
        this.numeroFactura = numeroFactura;
        this.ciResponsable = ciResponsable;
    }

    public double getCostoTotal() {
        return cantidad * costoUnitario;
    }

    @Override
    public String toString() {
        return descripcion + "#" + cantidad + "#" + costoUnitario + "#" + fechaAdquisicion + "#" + numeroFactura + "#" + ciResponsable;
    }
}

class Inventario {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Equipo> equipos = new ArrayList<>();

        System.out.println("Ingrese la data de los equipos en el siguiente formato:");
        System.out.println("\"dd\"/\"mm\"/\"aaaa\"");
        System.out.println("Para terminar la entrada, ingrese una línea vacía.");

        while (true) {
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                break;
            }
            try {
                String[] parts = input.split("#");
                String descripcion = parts[0];
                int cantidad = Integer.parseInt(parts[1]);
                double costoUnitario = Double.parseDouble(parts[2]);
                String fechaAdquisicion = parts[3];
                String numeroFactura = parts[4];
                String ciResponsable = parts[5];
                Equipo equipo = new Equipo(descripcion, cantidad, costoUnitario, fechaAdquisicion, numeroFactura, ciResponsable);
                equipos.add(equipo);
            } catch (Exception e) {
                System.out.println("Error en la entrada: " + e.getMessage());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventario.txt"))) {
            for (Equipo equipo : equipos) {
                writer.write(equipo.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }

        Map<String, List<Equipo>> equiposPorResponsable = new HashMap<>();
        for (Equipo equipo : equipos) {
            equiposPorResponsable
                    .computeIfAbsent(equipo.ciResponsable, k -> new ArrayList<>())
                    .add(equipo);
        }

        for (Map.Entry<String, List<Equipo>> entry : equiposPorResponsable.entrySet()) {
            String ciResponsable = entry.getKey();
            List<Equipo> equiposResponsable = entry.getValue();
            int totalEquipos = equiposResponsable.stream().mapToInt(e -> e.cantidad).sum();
            double totalMonto = equiposResponsable.stream().mapToDouble(Equipo::getCostoTotal).sum();
            System.out.println("Responsable CI: " + ciResponsable);
            System.out.println("Total de equipos: " + totalEquipos);
            System.out.println("Monto total: " + totalMonto + " bolívares");
        }
    }
}
