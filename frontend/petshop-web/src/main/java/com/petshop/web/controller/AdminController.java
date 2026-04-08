package com.petshop.web.controller;

import com.petshop.web.dto.*;
import com.petshop.shared.dto.*;
import com.petshop.web.service.CatalogClient;
import com.petshop.web.service.CommerceClient;
import com.petshop.web.service.UserAccountService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CatalogClient catalogClient;
    private final CommerceClient commerceClient;
    private final UserAccountService userAccountService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("catalogSummary", catalogClient.adminSummary());
        model.addAttribute("commerceSummary", commerceClient.analytics());
        model.addAttribute("userCount", userAccountService.users().size());
        return "admin/dashboard";
    }

    @GetMapping("/catalog")
    public String catalog(Model model) {
        model.addAttribute("products", catalogClient.products(null, null, null, null, null, 0, 20, "name"));
        model.addAttribute("pets", catalogClient.pets(null, null, 0, 20));
        model.addAttribute("services", catalogClient.services());
        model.addAttribute("posts", catalogClient.blog(0, 20));
        model.addAttribute("faqs", catalogClient.faqs(null));
        model.addAttribute("productCategories", catalogClient.productCategories());
        model.addAttribute("petCategories", catalogClient.petCategories());
        model.addAttribute("productForm", new AdminProductForm());
        model.addAttribute("petForm", new AdminPetForm());
        model.addAttribute("serviceForm", new AdminServiceForm());
        model.addAttribute("blogForm", new AdminBlogForm());
        model.addAttribute("faqForm", new AdminFaqForm());
        model.addAttribute("categoryForm", new AdminCategoryForm());
        return "admin/catalog";
    }

    @PostMapping("/catalog/product")
    public String saveProduct(@ModelAttribute AdminProductForm form, RedirectAttributes redirectAttributes) {
        catalogClient.saveProduct(form.getId(), new UpsertProductRequest(form.getName(), form.getSlug(), form.getDescription(), form.getSupplierName(), form.getPrice(), form.getStockQuantity(), form.isFeatured(), form.isActive(), form.getImageUrl(), form.getCategorySlug()));
        redirectAttributes.addFlashAttribute("successMessage", "Product saved.");
        return "redirect:/admin/catalog";
    }

    @PostMapping("/catalog/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        catalogClient.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "Product removed.");
        return "redirect:/admin/catalog";
    }

    @PostMapping("/catalog/pet")
    public String savePet(@ModelAttribute AdminPetForm form, RedirectAttributes redirectAttributes) {
        catalogClient.savePet(form.getId(), new UpsertPetRequest(form.getName(), form.getSlug(), form.getBreed(), form.getAgeInMonths(), form.getGender(), form.getPrice(), form.getSaleType(), form.getDescription(), form.isVaccinated(), form.isAvailable(), form.isFeatured(), form.getImageUrl(), form.getCategorySlug(), form.getVaccinations() == null ? java.util.List.of() : Arrays.stream(form.getVaccinations().split(",")).map(String::trim).filter(s -> !s.isBlank()).toList()));
        redirectAttributes.addFlashAttribute("successMessage", "Pet saved.");
        return "redirect:/admin/catalog";
    }

    @PostMapping("/catalog/pet/delete/{id}")
    public String deletePet(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        catalogClient.deletePet(id);
        redirectAttributes.addFlashAttribute("successMessage", "Pet removed.");
        return "redirect:/admin/catalog";
    }

    @PostMapping("/catalog/service")
    public String saveService(@ModelAttribute AdminServiceForm form, RedirectAttributes redirectAttributes) {
        catalogClient.saveService(form.getId(), new UpsertServiceRequest(form.getName(), form.getSlug(), form.getCategory(), form.getShortDescription(), form.getDescription(), form.getPrice(), form.getDurationMinutes(), form.isFeatured(), form.isActive(), form.getImageUrl()));
        redirectAttributes.addFlashAttribute("successMessage", "Service saved.");
        return "redirect:/admin/catalog";
    }

    @PostMapping("/catalog/service/delete/{id}")
    public String deleteService(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        catalogClient.deleteService(id);
        redirectAttributes.addFlashAttribute("successMessage", "Service removed.");
        return "redirect:/admin/catalog";
    }

    @PostMapping("/catalog/blog")
    public String saveBlog(@ModelAttribute AdminBlogForm form, RedirectAttributes redirectAttributes) {
        catalogClient.saveBlog(form.getId(), new UpsertBlogPostRequest(form.getTitle(), form.getSlug(), form.getExcerpt(), form.getContent(), form.getCategory(), form.getTags() == null ? java.util.List.of() : Arrays.stream(form.getTags().split(",")).map(String::trim).filter(tag -> !tag.isBlank()).toList(), form.getAuthorName(), form.getFeaturedImageUrl(), form.isPublished()));
        redirectAttributes.addFlashAttribute("successMessage", "Blog post saved.");
        return "redirect:/admin/catalog";
    }

    @PostMapping("/catalog/blog/delete/{id}")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        catalogClient.deleteBlog(id);
        redirectAttributes.addFlashAttribute("successMessage", "Blog post removed.");
        return "redirect:/admin/catalog";
    }

    @PostMapping("/catalog/faq")
    public String saveFaq(@ModelAttribute AdminFaqForm form, RedirectAttributes redirectAttributes) {
        catalogClient.saveFaq(form.getId(), new UpsertFaqRequest(form.getQuestion(), form.getAnswer(), form.getCategory(), form.getDisplayOrder(), form.isActive()));
        redirectAttributes.addFlashAttribute("successMessage", "FAQ saved.");
        return "redirect:/admin/catalog";
    }

    @PostMapping("/catalog/faq/delete/{id}")
    public String deleteFaq(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        catalogClient.deleteFaq(id);
        redirectAttributes.addFlashAttribute("successMessage", "FAQ removed.");
        return "redirect:/admin/catalog";
    }

    @PostMapping("/catalog/category/product")
    public String createProductCategory(@ModelAttribute AdminCategoryForm form, RedirectAttributes redirectAttributes) {
        catalogClient.createProductCategory(new UpsertCategoryRequest(form.getName(), form.getSlug(), form.getDescription()));
        redirectAttributes.addFlashAttribute("successMessage", "Product category created.");
        return "redirect:/admin/catalog";
    }

    @PostMapping("/catalog/category/pet")
    public String createPetCategory(@ModelAttribute AdminCategoryForm form, RedirectAttributes redirectAttributes) {
        catalogClient.createPetCategory(new UpsertCategoryRequest(form.getName(), form.getSlug(), form.getDescription()));
        redirectAttributes.addFlashAttribute("successMessage", "Pet category created.");
        return "redirect:/admin/catalog";
    }

    @GetMapping("/operations")
    public String operations(Model model) {
        model.addAttribute("orders", commerceClient.orders());
        model.addAttribute("inquiries", commerceClient.inquiries());
        model.addAttribute("messages", commerceClient.messages());
        model.addAttribute("bookings", commerceClient.bookings());
        model.addAttribute("adoptions", commerceClient.adoptions());
        return "admin/operations";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userAccountService.users());
        return "admin/users";
    }

    @PostMapping("/users/{userId}/role")
    public String assignRole(@PathVariable Long userId, @RequestParam String roleName, RedirectAttributes redirectAttributes) {
        userAccountService.assignRole(userId, roleName);
        redirectAttributes.addFlashAttribute("successMessage", "User role updated.");
        return "redirect:/admin/users";
    }
}
