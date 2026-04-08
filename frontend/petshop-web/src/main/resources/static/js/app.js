document.addEventListener("DOMContentLoaded", () => {
  const navShell = document.querySelector("[data-nav-shell]");
  if (navShell) {
    const syncNavState = () => {
      navShell.classList.toggle("is-scrolled", window.scrollY > 8);
    };

    syncNavState();
    window.addEventListener("scroll", syncNavState, { passive: true });
  }

  const revealItems = document.querySelectorAll("[data-reveal]");
  if (revealItems.length) {
    const revealObserver = new IntersectionObserver(entries => {
      entries.forEach(entry => {
        if (!entry.isIntersecting) {
          return;
        }
        entry.target.classList.add("is-visible");
        revealObserver.unobserve(entry.target);
      });
    }, {
      threshold: 0.15
    });

    revealItems.forEach(item => revealObserver.observe(item));
  }

  const searchInput = document.querySelector("[data-faq-search]");
  if (searchInput) {
    searchInput.addEventListener("input", event => {
      const query = event.target.value.toLowerCase();
      document.querySelectorAll("[data-faq-item]").forEach(item => {
        const text = item.textContent.toLowerCase();
        item.style.display = text.includes(query) ? "" : "none";
      });
    });
  }

  document.querySelectorAll("[data-fill-form]").forEach(button => {
    button.addEventListener("click", () => {
      const target = document.getElementById(button.dataset.fillForm);
      if (!target) return;
      Object.entries(button.dataset).forEach(([key, value]) => {
        if (!key.startsWith("field")) return;
        const name = key.replace("field", "");
        const normalized = name.charAt(0).toLowerCase() + name.slice(1);
        const input = target.querySelector(`[name='${normalized}']`);
        if (input) input.value = value;
      });
    });
  });
});
