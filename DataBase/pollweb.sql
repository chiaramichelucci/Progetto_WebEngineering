-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Set 14, 2020 alle 14:50
-- Versione del server: 10.4.8-MariaDB
-- Versione PHP: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pollweb`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `amministratore`
--

CREATE TABLE `amministratore` (
  `id` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

--
-- Dump dei dati per la tabella `amministratore`
--

INSERT INTO `amministratore` (`id`, `email`, `password`) VALUES
(1, 'administrator@pollweb.it', 'administrator');

-- --------------------------------------------------------

--
-- Struttura della tabella `domanda`
--

CREATE TABLE `domanda` (
  `id` int(11) NOT NULL,
  `testo` varchar(250) NOT NULL,
  `nota` varchar(250) NOT NULL,
  `tipo` varchar(8) DEFAULT NULL,
  `obbligatoria` tinyint(1) NOT NULL,
  `id_sondaggio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

--
-- Dump dei dati per la tabella `domanda`
--

INSERT INTO `domanda` (`id`, `testo`, `nota`, `tipo`, `obbligatoria`, `id_sondaggio`) VALUES
(1, 'Il tuo nome?', 'Inserire grazie.', NULL, 1, 1),
(2, 'Che voto dai a questo progetto?', '', NULL, 1, 2),
(3, 'Seleziona il sesso.', '', NULL, 1, 1),
(4, 'Hai suggerimenti?', 'scrivili sotto', NULL, 1, 2),
(11, 'Domanda prova text?', 'domanda di prova per i campi text', 'Text', 0, 18),
(12, 'Domanda prova radio?', 'domanda di prova per i campi radio', 'Radio', 0, 18),
(13, 'Domanda prova number?', 'domanda di prova per i campi number', 'Number', 0, 18),
(14, 'Domanda prova checkbox', 'domanda di prova per i campi checkbox', 'Checkbox', 0, 18),
(15, 'Domanda prova text?', 'domanda di prova per i campi text', 'Text', 0, 19),
(16, 'Domanda prova checkbox', 'domanda di prova per i campi checkbox', 'Radio', 0, 19),
(17, 'Domanda prova radio?', 'domanda di prova per i campi checkbox', 'Radio', 0, 20),
(18, 'Domanda prova radio?', 'domanda di prova per i campi checkbox', 'Radio', 0, 21),
(19, 'Domanda prova radio?', 'domanda di prova per i campi checkbox', 'Radio', 0, 22),
(20, 'Domanda prova radio?', 'domanda di prova per i campi checkbox', 'Radio', 0, 23),
(21, 'funziona?', 'spero', 'Radio', 0, 26),
(22, 'Domanda testo?', 'testo della nota text', 'Text', 0, 27),
(23, 'Domanda radio?', 'testo della nota radio', 'Radio', 0, 27),
(24, 'domanda text', '', 'Text', 0, 28),
(25, 'domanda radio', '', 'Radio', 0, 28),
(26, 'domanda 1', 'qwe', 'Text', 0, 29),
(27, 'domanda radio', 'asd', 'Radio', 0, 29),
(28, 'domanda checkbox 2', 'qaz', 'Checkbox', 0, 29),
(29, 'domanda 1', 'nota 1', 'Text', 0, 30),
(30, 'domanda 2', 'nota 2', 'Radio', 0, 30),
(31, 'domanda 3', 'nota 3', 'Checkbox', 0, 30),
(34, 'domanda 4', 'nota 4', 'Radio', 0, 30),
(35, 'domanda 5', 'nota 5', 'Checkbox', 0, 30);

-- --------------------------------------------------------

--
-- Struttura della tabella `interagisce`
--

CREATE TABLE `interagisce` (
  `utente` int(11) NOT NULL,
  `tipo` enum('partecipante','responsabile') DEFAULT NULL,
  `sondaggio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

--
-- Dump dei dati per la tabella `interagisce`
--

INSERT INTO `interagisce` (`utente`, `tipo`, `sondaggio`) VALUES
(2, NULL, 29),
(2, NULL, 30);

-- --------------------------------------------------------

--
-- Struttura della tabella `opzione`
--

CREATE TABLE `opzione` (
  `id` int(11) NOT NULL,
  `id_domanda` int(11) NOT NULL,
  `testo` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `opzione`
--

INSERT INTO `opzione` (`id`, `id_domanda`, `testo`) VALUES
(1, 23, 'Radio 1'),
(2, 23, 'Radio 2'),
(3, 25, 'opzione radio 1'),
(4, 25, 'opzione radio 2'),
(5, 27, 'qwe1'),
(6, 27, 'qwe2'),
(7, 28, 'zxc1'),
(8, 28, 'zxc2'),
(9, 30, 'prova radio 1'),
(10, 30, 'prova radio 2'),
(11, 31, 'prova checkbox 1'),
(12, 31, 'prova checkbox 2'),
(13, 31, 'prova checkbox 3'),
(14, 34, 'Radio 2 1'),
(15, 34, 'Radio 2 2'),
(16, 35, 'Checkbox 2 1'),
(17, 35, 'Checkbox 2 2');

-- --------------------------------------------------------

--
-- Struttura della tabella `risposta`
--

CREATE TABLE `risposta` (
  `id` int(11) NOT NULL,
  `id_domanda` int(11) NOT NULL,
  `id_sondaggio` int(11) NOT NULL,
  `id_utente` int(11) DEFAULT NULL,
  `testo_risposta` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `sondaggio`
--

CREATE TABLE `sondaggio` (
  `id` int(11) NOT NULL,
  `titolo` varchar(100) NOT NULL,
  `testo_apertura` varchar(256) DEFAULT NULL,
  `testo_chiusura` varchar(256) DEFAULT NULL,
  `disponibile` tinyint(1) NOT NULL,
  `modalita` varchar(10) NOT NULL,
  `url` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

--
-- Dump dei dati per la tabella `sondaggio`
--

INSERT INTO `sondaggio` (`id`, `titolo`, `testo_apertura`, `testo_chiusura`, `disponibile`, `modalita`, `url`) VALUES
(1, 'Test', NULL, NULL, 1, '', ''),
(2, 'Test2', NULL, NULL, 1, '', ''),
(18, 'Sondaggio Prova', NULL, NULL, 0, 'Aperto', ''),
(19, 'Sondaggio Prova 0', NULL, NULL, 0, 'Aperto', ''),
(20, 'Sondaggio Prova 1', NULL, NULL, 0, 'Aperto', ''),
(21, 'Sondaggio Prova 12', NULL, NULL, 0, 'Aperto', ''),
(22, 'Sondaggio Prova 13', NULL, NULL, 0, 'Aperto', ''),
(23, 'Sondaggio Prova 14', NULL, NULL, 0, 'Aperto', ''),
(24, 'Prova freemarker', NULL, NULL, 0, 'Aperto', ''),
(25, 'Prova freemarker 2', NULL, NULL, 0, 'Aperto', ''),
(26, 'Prova freemarker 3', NULL, NULL, 0, 'Privato', ''),
(27, 'Sondaggio Prova Compilazione', NULL, NULL, 0, 'Aperto', ''),
(28, 'Prova Compilazione', NULL, NULL, 0, 'Aperto', ''),
(29, 'prova inserimento', NULL, NULL, 0, 'Aperto', ''),
(30, 'prova inserimento 2', NULL, NULL, 0, 'Aperto', '');

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `id` int(11) NOT NULL,
  `cognome` varchar(25) NOT NULL,
  `nome` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `tipo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`id`, `cognome`, `nome`, `email`, `password`, `tipo`) VALUES
(1, 'michelucci', 'chiara', 'chiara@pollweb.it', 'chiara', 'responsabile'),
(2, 'stratulat', 'dragos', 'dragos@pollweb.it', 'dragos', 'responsabile'),
(3, 'Marco', 'Polo', 'marco.polo@generico.it', 'marcopolo', 'partecipante');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `amministratore`
--
ALTER TABLE `amministratore`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `domanda`
--
ALTER TABLE `domanda`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sondaggio` (`id_sondaggio`);

--
-- Indici per le tabelle `interagisce`
--
ALTER TABLE `interagisce`
  ADD KEY `utente` (`utente`),
  ADD KEY `sondaggio` (`sondaggio`);

--
-- Indici per le tabelle `opzione`
--
ALTER TABLE `opzione`
  ADD PRIMARY KEY (`id`),
  ADD KEY `opzione_fk_1` (`id_domanda`);

--
-- Indici per le tabelle `risposta`
--
ALTER TABLE `risposta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_domanda` (`id_domanda`),
  ADD KEY `id_sondaggio` (`id_sondaggio`),
  ADD KEY `id_utente` (`id_utente`);

--
-- Indici per le tabelle `sondaggio`
--
ALTER TABLE `sondaggio`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `titolo_unico` (`titolo`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `amministratore`
--
ALTER TABLE `amministratore`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `domanda`
--
ALTER TABLE `domanda`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT per la tabella `opzione`
--
ALTER TABLE `opzione`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT per la tabella `risposta`
--
ALTER TABLE `risposta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `sondaggio`
--
ALTER TABLE `sondaggio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `domanda`
--
ALTER TABLE `domanda`
  ADD CONSTRAINT `domanda_fk_1` FOREIGN KEY (`id_sondaggio`) REFERENCES `sondaggio` (`id`) ON UPDATE CASCADE;

--
-- Limiti per la tabella `interagisce`
--
ALTER TABLE `interagisce`
  ADD CONSTRAINT `interagisce_ibfk_1` FOREIGN KEY (`sondaggio`) REFERENCES `sondaggio` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `interagisce_ibfk_2` FOREIGN KEY (`utente`) REFERENCES `utente` (`id`) ON UPDATE CASCADE;

--
-- Limiti per la tabella `opzione`
--
ALTER TABLE `opzione`
  ADD CONSTRAINT `opzione_fk_1` FOREIGN KEY (`id_domanda`) REFERENCES `domanda` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `risposta`
--
ALTER TABLE `risposta`
  ADD CONSTRAINT `risposta_fbk_1` FOREIGN KEY (`id_domanda`) REFERENCES `domanda` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `risposta_fbk_2` FOREIGN KEY (`id_sondaggio`) REFERENCES `sondaggio` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `risposta_fbk_3` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `risposta_ibfk_1` FOREIGN KEY (`id_domanda`) REFERENCES `domanda` (`id`),
  ADD CONSTRAINT `risposta_ibfk_2` FOREIGN KEY (`id_sondaggio`) REFERENCES `sondaggio` (`id`),
  ADD CONSTRAINT `risposta_ibfk_3` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
