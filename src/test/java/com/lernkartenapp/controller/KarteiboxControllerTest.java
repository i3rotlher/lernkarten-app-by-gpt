// package com.lernkartenapp.controller;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.hamcrest.Matchers.hasSize;

// import java.util.ArrayList;
// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContext;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// import com.lernkartenapp.model.Karteibox;
// import com.lernkartenapp.service.KarteiboxService;

// public class KarteiboxControllerTest {

//     private MockMvc mockMvc;

//     @Mock
//     private KarteiboxService karteiboxService;

//     @InjectMocks
//     private KarteiboxController karteiboxController;

//     @Mock
//     private Authentication authentication;

//     @Mock
//     private SecurityContext securityContext;

//     @BeforeEach
//     public void setup() {
//         MockitoAnnotations.openMocks(this);
//         authentication = mock(Authentication.class);
//         when(authentication.getName()).thenReturn("testUser");

//         SecurityContext securityContext = mock(SecurityContext.class);
//         when(securityContext.getAuthentication()).thenReturn(authentication);
//         SecurityContextHolder.setContext(securityContext);

//         mockMvc = MockMvcBuilders.standaloneSetup(karteiboxController).build();
//     }

//     @Test
//     public void testCreateKarteibox() throws Exception {
//         Karteibox mockKarteibox = new Karteibox();
//         mockKarteibox.setId("1");
//         mockKarteibox.setUserId("user123");

//         when(karteiboxService.createKarteibox(any(Karteibox.class))).thenReturn(mockKarteibox);
//         when(authentication.getName()).thenReturn("user123");

//         mockMvc.perform(post("/karteiboxen/")
//                 .contentType("application/json")
//                 .content("{\"name\":\"Test Karteibox\",\"beschreibung\":\"Test Beschreibung\", \"userId\":\"user123\"}"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value("1"));
//     }

//     @Test
//     public void testGetKarteiboxenByUser() throws Exception {
//         List<Karteibox> karteiboxList = new ArrayList<>();
//         karteiboxList.add(new Karteibox());
//         String userId = "testUser";
    
//         when(karteiboxService.getKarteiboxenByUser(userId)).thenReturn(karteiboxList);
//         when(authentication.getName()).thenReturn(userId);
    
//         mockMvc.perform(get("/karteiboxen/user/" + userId))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$", hasSize(1)));
//     }    

//     @Test
//     public void testDeleteKarteibox() throws Exception {
//         String karteiboxId = "karteibox123";
    
//         doNothing().when(karteiboxService).deleteKarteibox(karteiboxId);
    
//         mockMvc.perform(delete("/karteiboxen/" + karteiboxId))
//                 .andExpect(status().isOk());
//     }

//     @Test
//     public void testGetProgress() throws Exception {
//         String karteiboxId = "karteibox123";
//         double progress = 50.0;

//         when(karteiboxService.calculateProgress(karteiboxId)).thenReturn(progress);

//         mockMvc.perform(get("/karteiboxen/" + karteiboxId + "/progress"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$").value(progress));
//     }
// }
