import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int count;

    void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    void save(Resume r) {
        storage[count] = r;
        count++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < count; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < count; i++) {
            Resume innerCounter = storage[i];
            if (innerCounter.uuid.equals(uuid)) {
                storage[i] = null;
                count--;
                // If there are elements after deleted -> moving elements left
                while (storage[i + 1] != null) {
                    storage[i] = storage[i + 1];
                    storage[i + 1] = null;
                    i++;
                }
                return;
            }
        }
        System.out.println("Данный uuid отсутствует в массиве!");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resume = new Resume[count];
        // Add "Not null" elements to new array
        for (int i = 0; i < count; i++) {
            resume[i] = storage[i];
        }
        return resume;
    }

    int size() {
        return count;
    }
}
