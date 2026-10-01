/* Havila Learning – Hero Gallery
   - troca automática (data-interval, por defeito 4000 ms), em loop contínuo
   - setas, indicadores e deslize (swipe) no telemóvel
   - pausa com o rato por cima (só rato, para não bloquear no telemóvel),
     com foco por teclado e quando o separador fica em segundo plano */
(function () {
  function init(gallery) {
    var slides = [].slice.call(gallery.querySelectorAll('.hero-slide'));
    if (slides.length < 2) return;

    var interval = parseInt(gallery.getAttribute('data-interval'), 10) || 4000;
    var dotLabel = gallery.getAttribute('data-dot-label') || 'Go to photo';
    var dotsWrap = gallery.querySelector('.hg-dots');
    var prevBtn = gallery.querySelector('.hg-prev');
    var nextBtn = gallery.querySelector('.hg-next');
    var current = 0;
    var timer = null;
    var hovering = false;

    var dots = slides.map(function (_, n) {
      var b = document.createElement('button');
      b.type = 'button';
      b.className = 'hg-dot';
      b.setAttribute('aria-label', dotLabel + ' ' + (n + 1));
      b.addEventListener('click', function () { show(n); restart(); });
      if (dotsWrap) dotsWrap.appendChild(b);
      return b;
    });

    function show(n) {
      slides[current].classList.remove('is-active');
      dots[current].classList.remove('is-active');
      dots[current].removeAttribute('aria-current');
      current = (n + slides.length) % slides.length;
      slides[current].classList.add('is-active');
      dots[current].classList.add('is-active');
      dots[current].setAttribute('aria-current', 'true');
    }

    function stop() { if (timer) { clearInterval(timer); timer = null; } }
    function start() {
      stop();
      if (hovering || document.hidden) return;
      timer = setInterval(function () { show(current + 1); }, interval);
    }
    function restart() { start(); }

    show(0);

    if (prevBtn) prevBtn.addEventListener('click', function () { show(current - 1); restart(); });
    if (nextBtn) nextBtn.addEventListener('click', function () { show(current + 1); restart(); });

    // Pausa com o rato por cima (computador)
    gallery.addEventListener('pointerenter', function (e) {
      if (e.pointerType === 'mouse') { hovering = true; stop(); }
    });
    gallery.addEventListener('pointerleave', function (e) {
      if (e.pointerType === 'mouse') { hovering = false; start(); }
    });

    // Pausa com foco no teclado
    gallery.addEventListener('focusin', function () { hovering = true; stop(); });
    gallery.addEventListener('focusout', function () { hovering = false; start(); });

    // Deslizar no telemóvel
    var startX = null;
    gallery.addEventListener('touchstart', function (e) {
      startX = e.touches[0].clientX;
      stop();
    }, { passive: true });
    gallery.addEventListener('touchend', function (e) {
      if (startX !== null) {
        var dx = e.changedTouches[0].clientX - startX;
        if (Math.abs(dx) > 40) show(current + (dx < 0 ? 1 : -1));
      }
      startX = null;
      start();
    }, { passive: true });

    // Não gastar recursos com o separador em segundo plano
    document.addEventListener('visibilitychange', start);

    // Carregar as restantes fotos depois da página, para não atrasar o Home
    window.addEventListener('load', function () {
      slides.forEach(function (img) { img.loading = 'eager'; });
    });

    start();
  }

  function ready() {
    [].slice.call(document.querySelectorAll('[data-hero-gallery]')).forEach(init);
  }
  if (document.readyState === 'loading') document.addEventListener('DOMContentLoaded', ready);
  else ready();
})();
