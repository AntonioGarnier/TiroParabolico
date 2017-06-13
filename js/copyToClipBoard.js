function copyToClipBoard(id_elemento) {
  var aux = document.createElement("input");
  aux.setAttribute("value", "javaws https://antoniogarnier.github.io/TiroParabolico/public/tiro.jnlp");
  document.body.appendChild(aux);
  aux.select();
  document.execCommand("copy");
  document.body.removeChild(aux);
}
