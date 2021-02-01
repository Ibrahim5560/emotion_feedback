import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/emo-system">
      <Translate contentKey="global.menu.entities.emoSystem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/emo-system-services">
      <Translate contentKey="global.menu.entities.emoSystemServices" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/emo-center">
      <Translate contentKey="global.menu.entities.emoCenter" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/emo-users">
      <Translate contentKey="global.menu.entities.emoUsers" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/emo-messages">
      <Translate contentKey="global.menu.entities.emoMessages" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/emo-message-feedback">
      <Translate contentKey="global.menu.entities.emoMessageFeedback" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
