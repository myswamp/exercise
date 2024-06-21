package junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitTest {
    @Test
    public void testMock()  {

        List<Integer> myList = mock(ArrayList.class);

        doCallRealMethod().when(myList).add(any(Integer.class));

        myList.add(15);
        myList.size();

        verify(myList, times(1)).add(0, 15);
        verify(myList, times(1)).size();

        Integer result = myList.get(0);
        assertEquals(15, result);
    }


}
