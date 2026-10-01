
const toggle=document.querySelector('.mobile-toggle');
const menu=document.querySelector('.menu');
if(toggle && menu){
  toggle.addEventListener('click',()=>menu.classList.toggle('open'));
}

(function(){
  const menu=document.querySelector('.menu');
  if(!menu || menu.querySelector('.language-switch')) return;
  const path=window.location.pathname.replace(/\\/+$/,'/') || '/';
  const map={
    '/':'/pt/',
    '/about/':'/pt/about/',
    '/blog/':'/pt/blog/',
    '/blog/learning-activities-for-3-year-olds/':'/pt/blog/atividades-de-aprendizagem-para-criancas-de-3-anos/',
    '/blog/learning-activities-for-4-year-olds/':'/pt/blog/atividades-de-aprendizagem-para-criancas-de-4-anos/',
    '/contact/':'/pt/contact/',
    '/ebooks/':'/pt/ebooks/',
    '/free-resources/':'/pt/free-resources/',
    '/learning-activities/':'/pt/learning-activities/',
    '/learning-activities/age-3/':'/pt/blog/atividades-de-aprendizagem-para-criancas-de-3-anos/',
    '/learning-activities/age-4/':'/pt/blog/atividades-de-aprendizagem-para-criancas-de-4-anos/',
    '/learning-activities/age-5/':'/pt/learning-activities/',
    '/learning-activities/age-6-plus/':'/pt/learning-activities/'
  };
  const reverse={};
  Object.keys(map).forEach(en=>reverse[map[en]]=en);
  let en,pt;
  if(path.startsWith('/pt/')){
    pt=path; en=reverse[path] || '/';
  }else{
    en=path; pt=map[path] || '/pt/';
  }
  const wrap=document.createElement('span');
  wrap.className='language-switch';
  const aEn=document.createElement('a'); aEn.href=en; aEn.textContent='EN';
  const sep=document.createElement('span'); sep.setAttribute('aria-hidden','true'); sep.textContent=' | ';
  const aPt=document.createElement('a'); aPt.href=pt; aPt.textContent='PT';
  (path.startsWith('/pt/') ? aPt : aEn).className='active';
  wrap.append(aEn,sep,aPt);
  menu.appendChild(wrap);
})();
