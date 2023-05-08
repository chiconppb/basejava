import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int count=0; //Counter for added resume

    void clear() {
        // Replace all elements by null
        Arrays.fill(storage, null);
        // Drop the counter
        count=0;
    }

    void save(Resume r) {
        // Save element to next empty slot
        storage[count] = r;
        // Increase amount of array elements
        count++;
    }

    Resume get(String uuid) {
        // Check every resume uuid
        for (Resume r : storage)
        {
            try{
                // Return resume if uuid of element matched with requested uuid
                if (r.uuid.equals(uuid)) {
                return r;
                }
            } // Catch NullPointerException and ignore it
            catch (NullPointerException ignored){}
        }
        // Return null if there are no matches
        return null;
    }


    void delete(String uuid) {
        for(int i=0; i<storage.length; i++){
            // Added counter for checking element position in array
            Resume innerCounter = storage[i];
            // If uuid of element and required uuid are the same -> delete element
            if(innerCounter.uuid.equals(uuid)){
                storage[i]=null;
                // Reduce counter of exists resumes
                count--;
                // If there are elements after deleted -> moving elements left
                while (storage[i+1]!=null){
                    storage[i] = storage[i+1];
                    storage[i+1] = null;
                    i++;
                }
                return;
            }
        } System.out.println("Данный uuid отсутствует в массиве!");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        // Create an array for exact count of elements
        Resume[] resume = new Resume[count];
        // Add "Not null" elements to new array
        for(int i=0; i<resume.length; i++){
            resume[i] = storage[i];
        }
        return resume;
    }

    int size() {
        // Return amount of exists resumes
        return count;
    }
}
